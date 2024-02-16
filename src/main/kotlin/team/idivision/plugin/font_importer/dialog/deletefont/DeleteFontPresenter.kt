package team.idivision.plugin.font_importer.dialog.deletefont

import team.idivision.plugin.font_importer.data.DEFAULT_MODULE_NAME
import team.idivision.plugin.font_importer.data.FileManager
import team.idivision.plugin.font_importer.dialog.core.BasePresenter
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.files.FileUtils


class DeleteFontPresenter(
    private val fileUtils: FileUtils,
    private val fileManager: FileManager
) : BasePresenter<DeleteFontAgreement.View>(), DeleteFontAgreement.Presenter {

    private val state: DeleteFontsState = DeleteFontsState()

    override fun getState(): DeleteFontsState = state

    override fun getModuleDropDownItems(): Array<String> = fileManager.getAvailableModuleNames()

    override fun deleteFontsFromModule() {
        val module = getState().selectedModule

        if (!fileManager.hasResourcesDir(module)) {
            view?.showNoResFolderError(module)
            return
        }

        if (!fileManager.hasFontDir(module)) {
            view?.showNoFontFolderError(module)
            return
        }

        val fontDir = fileManager.findFontDir(module)
        val hasNoFonts = fontDir?.children.isNullOrEmpty()

        if (hasNoFonts) {
            view?.showEmptyFontFolderError(module)
            return
        }

        fileUtils.deleteFonts(fontDir) { path, filesCount ->
            val location = " ${Localization.getString("notification.message.part.in").plus(" $path")}"
            view?.showDeleteSuccess(filesCount, location)
        }
    }
}

data class DeleteFontsState(
    var selectedModule: String = DEFAULT_MODULE_NAME,
)