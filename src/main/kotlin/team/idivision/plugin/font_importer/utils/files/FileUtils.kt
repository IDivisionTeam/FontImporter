package team.idivision.plugin.font_importer.utils.files

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.extensions.toSimpleUrl
import java.io.IOException


class FileUtils(private val project: Project?) {
    private val forbiddenSymbolsRegex: Regex = ("[^a-z_]").toRegex()
    private val humpsRegex = ("(?<=.)(?=\\p{Upper})").toRegex()
    private val multipleUnderscoresRegex = ("_{2,}").toRegex()

    @Throws(IOException::class, NullPointerException::class)
    fun copyFonts(
        newParent: VirtualFile?,
        selectedFonts: List<VirtualFile>,
        successListener: (path: String, filesCount: Int, replacedFilesCount: Int) -> Unit
    ) {
        checkNotNull(newParent) {
            NullPointerException(Localization.getString("exception.message.new_path_null"))
        }

        val cleanFileNames = cleanFileNames(selectedFonts)
        val existingFonts = newParent.children.associateBy { it.nameWithoutExtension }

        var importedFontsCount = 0
        var replacedFontsCount = 0

        for (index in selectedFonts.indices) {
            val selectedFontFile = selectedFonts[index]
            val newName = cleanFileNames[index]
            val existingFontFile = existingFonts[newName]

            WriteCommandAction.runWriteCommandAction(project) {
                if (existingFontFile != null) {
                    existingFontFile.delete(this)
                    replacedFontsCount++
                    importedFontsCount--
                }

                selectedFontFile.copy(
                    this,
                    newParent,
                    "$newName.${selectedFontFile.extension}",
                )
            }
            importedFontsCount++
        }

        successListener(newParent.toSimpleUrl(), importedFontsCount, replacedFontsCount)
    }

    @Throws(IOException::class, NullPointerException::class)
    fun deleteFonts(
        parent: VirtualFile?,
        successListener: (path: String, filesCount: Int) -> Unit
    ) {
        checkNotNull(parent) {
            NullPointerException(Localization.getString("exception.message.parent_path_null"))
        }

        val filesCount = parent.children.size
        WriteCommandAction.runWriteCommandAction(project) {
            parent.children.forEach { it.delete(this) }
        }

        successListener(parent.toSimpleUrl(), filesCount)
    }

    fun cleanFileNames(files: List<VirtualFile>): List<String> {
        val cleanNames = mutableListOf<String>()
            .apply {
                files.forEach { file ->
                    val replaceDashes = file.nameWithoutExtension.replace("-", "_").toSnakeCase()
                    val replaceSymbols = replaceDashes.replace(forbiddenSymbolsRegex, "")
                    val cleanName = replaceSymbols.replace(multipleUnderscoresRegex, "_")

                    add(cleanName)
                }
            }

        return enumerateDuplicates(cleanNames)
    }

    private fun enumerateDuplicates(fileNames: List<String>): List<String> {
        val sortedNames = fileNames.sorted().toMutableList()
        var previous = sortedNames.first()
        var counter = 0

        for (index in sortedNames.indices) {
            val current = sortedNames[index]

            if (current == previous) {
                counter++
            } else {
                counter = 0
            }

            if (counter > 1) {
                sortedNames[index] = "$current${counter - 1}"
            }

            previous = current
        }

        return sortedNames
    }

    private fun String.toSnakeCase() = replace(humpsRegex, "_").lowercase()
}