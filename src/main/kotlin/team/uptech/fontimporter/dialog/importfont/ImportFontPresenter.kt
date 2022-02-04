package team.uptech.fontimporter.dialog.importfont

import com.intellij.openapi.vfs.VirtualFile
import team.uptech.fontimporter.dialog.core.BasePresenter
import team.uptech.fontimporter.utils.files.FileUtils
import team.uptech.fontimporter.utils.files.ResourcesStatus
import team.uptech.fontimporter.utils.files.ResourcesUtil


class ImportFontPresenter(
    private val fileUtils: FileUtils,
    private val resourcesUtil: ResourcesUtil
) : BasePresenter<ImportFontAgreement.View>(), ImportFontAgreement.Presenter {

    private val filesSelected: MutableList<VirtualFile> = mutableListOf()

    override fun getModuleDropDownItems(): Array<String> {
        return resourcesUtil.getAvailableModuleNames().toTypedArray()
    }

    private fun getSelectedFiles(): String {
        return filesSelected.joinToString(
            separator = SEPARATOR,
            transform = { font -> font.nameWithoutExtension }
        )
    }

    private fun getRenamedSelectedFiles(): String {
        return fileUtils.cleanFileNames(filesSelected).joinToString(SEPARATOR)
    }

    override fun saveSelectedFonts(selectedFonts: List<VirtualFile>) {
        if (filesSelected.isNotEmpty()) filesSelected.clear()
        filesSelected.addAll(selectedFonts)

        view?.updateDialogUi(getSelectedFiles(), getRenamedSelectedFiles())
    }

    override fun importSelectedFontToModule(module: String) {
        when (resourcesUtil.createAndGetFontDir(module)) {
            ResourcesStatus.NoResDir -> {
                view?.showNoResFolderError(module)
            }
            ResourcesStatus.NoFontDir -> {
                view?.showNoFontFolderError(module)
            }
            ResourcesStatus.Success -> {
                fileUtils.copyFonts(resourcesUtil.fontDir, filesSelected) { path, filesCount, replacedFilesCount ->
                    view?.showImportSuccess(filesCount, replacedFilesCount, path)
                }
            }
        }
    }

    companion object {
        private const val SEPARATOR = "\n"
    }
}