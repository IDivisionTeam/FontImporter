package team.idivision.plugin.font_importer.utils.files

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.extensions.toSimpleUrl
import java.io.IOException


class FileUtils(private val project: Project?) {
    private val unnecessarySymbolsRegex: Regex = ("[^a-z_]").toRegex()
    private val humpsRegex = ("(?<=.)(?=\\p{Upper})").toRegex()
    private val underscoreRegex = ("_{2,}").toRegex()

    @Throws(IOException::class, NullPointerException::class)
    fun copyFonts(
        newParent: VirtualFile?,
        selectedFonts: List<VirtualFile>,
        successListener: (path: String, filesCount: Int, replacedFilesCount: Int) -> Unit
    ) {
        if (newParent == null) throw NullPointerException(Localization.getString("exception.message.new_path_null"))

        val cleanFileNames = cleanFileNames(selectedFonts)

        var importedFontsCount = 0
        var replacedFontsCount = 0
        selectedFonts.forEachIndexed { index, file ->
            val newName = cleanFileNames[index]

            WriteCommandAction.runWriteCommandAction(project) {
                newParent.children.firstOrNull { it.nameWithoutExtension == newName }?.delete(this)?.also {
                    replacedFontsCount++
                    importedFontsCount--
                }

                file.copy(this, newParent, "$newName.${file.extension}")
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
        if (parent == null) throw NullPointerException(Localization.getString("exception.message.parent_path_null"))

        val filesCount = parent.children.size
        WriteCommandAction.runWriteCommandAction(project) {
            parent.children.forEach { it.delete(this) }
        }

        successListener(parent.toSimpleUrl(), filesCount)
    }

    fun cleanFileNames(files: List<VirtualFile>): List<String> {
        val newFileNames = mutableListOf<String>()

        files.forEach { file ->
            val replaceDash = file.nameWithoutExtension.replace("-", "_").toSnakeCase()
            val replaceSymbols = replaceDash.replace(unnecessarySymbolsRegex, "")
            val cleanName = replaceSymbols.replace(underscoreRegex, "_")

            newFileNames.add(cleanName)
        }

        return fixFileNameDuplicates(newFileNames).toList()
    }

    private fun fixFileNameDuplicates(fileNames: List<String>): List<String> {
        val hasDuplicates = fileNames.distinct().count() != fileNames.size
        if (!hasDuplicates) return fileNames

        val fixedFileNames = fileNames.toMutableList()

        val fileNameIndexesWithDuplicates = fileNames.mapIndexed { index, i -> i to index }
            .groupBy { it.first }
            .map { it.value.map { it.second } }

        fileNameIndexesWithDuplicates.forEach { groupedFiles ->
            if (groupedFiles.size == 1) {
                fixedFileNames[groupedFiles.first()] = fileNames[groupedFiles.first()]
                return@forEach
            }

            var fileIndex = 1
            fixedFileNames[groupedFiles.first()] = fileNames[groupedFiles.first()]
            groupedFiles.drop(1).forEach { index ->
                fixedFileNames[index] = "${fileNames[index]}$fileIndex"
                fileIndex++
            }
        }

        return fixedFileNames.toList()
    }

    private fun String.toSnakeCase() = replace(humpsRegex, "_").lowercase()
}