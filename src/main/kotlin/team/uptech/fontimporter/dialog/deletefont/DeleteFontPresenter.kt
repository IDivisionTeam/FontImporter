package team.uptech.fontimporter.dialog.deletefont

import team.uptech.fontimporter.dialog.core.BasePresenter
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.files.FileUtils
import team.uptech.fontimporter.utils.files.ResourcesStatus
import team.uptech.fontimporter.utils.files.ResourcesUtil


class DeleteFontPresenter(
    private val fileUtils: FileUtils,
    private val resourcesUtil: ResourcesUtil
) : BasePresenter<DeleteFontAgreement.View>(), DeleteFontAgreement.Presenter {

    override fun getModuleDropDownItems(): Array<String> {
        return resourcesUtil.getAvailableModuleNames().toTypedArray()
    }

    override fun deleteFontsFromModule(module: String) {
        when (resourcesUtil.getFontDir(module)) {
            ResourcesStatus.NoResDir -> {
                view?.showNoResFolderError(module)
            }
            ResourcesStatus.NoFontDir -> {
                view?.showNoFontFolderError(module)
            }
            ResourcesStatus.Success -> {
                if (!resourcesUtil.hasFonts()) {
                    view?.showEmptyFontFolderError(module)
                    return
                }

                fileUtils.deleteFonts(resourcesUtil.fontDir) { path, filesCount ->
                    val location = " ${Localization.getString("notification.message.part.in").plus(" $path")}"
                    view?.showDeleteSuccess(filesCount, location)
                }
            }
        }
    }
}