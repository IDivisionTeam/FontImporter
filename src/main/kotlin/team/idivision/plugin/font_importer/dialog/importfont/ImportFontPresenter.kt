package team.idivision.plugin.font_importer.dialog.importfont

import com.intellij.openapi.vfs.VirtualFile
import team.idivision.plugin.font_importer.data.DEFAULT_MODULE_NAME
import team.idivision.plugin.font_importer.data.FileManager
import team.idivision.plugin.font_importer.dialog.core.BasePresenter
import team.idivision.plugin.font_importer.utils.files.FileUtils


class ImportFontPresenter(
    private val fileUtils: FileUtils,
    private val fileManager: FileManager,
) : BasePresenter<ImportFontAgreement.View>(), ImportFontAgreement.Presenter {
    private val filesSelected: MutableList<VirtualFile> = mutableListOf()
    private val state: ImportFontState = ImportFontState()

    override fun getState(): ImportFontState = state

    override fun getModuleDropDownItems(): Array<String> =
        fileManager.getAvailableModuleNames()

    private fun getSelectedFiles(): String = filesSelected.joinToString(
        separator = SEPARATOR,
        transform = { font -> font.nameWithoutExtension },
    )

    private fun getRenamedSelectedFiles(): String =
        fileUtils.cleanFileNames(filesSelected).joinToString(SEPARATOR)

    override fun saveSelectedFonts(selectedFonts: List<VirtualFile>) {
        if (filesSelected.isNotEmpty()) filesSelected.clear()
        filesSelected.addAll(selectedFonts)

        state.selectedFonts = getSelectedFiles()
        state.formattedFonts = getRenamedSelectedFiles()

        view?.updateTextFields()
        view?.toggleActionButtonState(isEnabled = true)
    }

    override fun importSelectedFontToModule() {
        val module = getState().selectedModule

        if (!fileManager.hasResourcesDir(module)) {
            fileManager.createResDir(module)
        }

        if (!fileManager.hasFontDir(module)) {
            fileManager.createFontDir(module)
        }

        fileUtils.copyFonts(
            fileManager.findFontDir(module),
            filesSelected
        ) { path, filesCount, replacedFilesCount ->
            view?.showImportSuccess(filesCount, replacedFilesCount, path)
        }
    }

    companion object {
        private const val SEPARATOR = "\n"
    }
}

data class ImportFontState(
    var selectedModule: String = DEFAULT_MODULE_NAME,
    var selectedFonts: String = "",
    var formattedFonts: String = "",
)