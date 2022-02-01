package team.uptech.fontimporter.dialog

import com.intellij.openapi.project.Project
import team.uptech.fontimporter.dialog.core.BaseDialog
import team.uptech.fontimporter.dialog.uidelegates.DeleteFontDelegate
import team.uptech.fontimporter.dialog.uidelegates.core.UiDelegate
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.buildDeleteSuccessNotification
import team.uptech.fontimporter.utils.files.FileUtils
import team.uptech.fontimporter.utils.files.ResourcesStatus
import team.uptech.fontimporter.utils.files.ResourcesUtil
import java.io.IOException
import javax.swing.JComponent


class DeleteFontDialog(project: Project?) : BaseDialog(project) {

    private val fileUtils: FileUtils = FileUtils(project)
    private val resourcesUtil: ResourcesUtil = ResourcesUtil(project)
    private val delegate: UiDelegate = DeleteFontDelegate(resourcesUtil) { selectedItem ->
        isOKActionEnabled = !selectedItem.isNullOrBlank()
    }

    init {
        title = Localization.getString("dialog.title.delete")
        setOKButtonText(Localization.getString("action.delete"))
        setCancelButtonText(Localization.getString("action.cancel"))
        isOKActionEnabled = false
        init()
    }

    override fun createCenterPanel(): JComponent {
        return delegate.buildDialogUi()
    }

    override fun doOKAction() {
        super.doOKAction()
        try {
            deleteFontsFromModule()
        } catch (error: IOException) {
            showError(project, error.message)
        }
    }

    private fun deleteFontsFromModule() {
        val module = delegate.getDropDownSelectedItem() ?: ResourcesUtil.DEFAULT_MODULE

        when (resourcesUtil.getFontDir(module)) {
            ResourcesStatus.NoResDir -> {
                showError(project, Localization.getString("exception.message.no_res_folder", module))
            }
            ResourcesStatus.NoFontDir -> {
                showError(project, Localization.getString("exception.message.no_font_folder", module))
            }
            ResourcesStatus.Success -> {
                if (!resourcesUtil.hasFonts()) {
                    showError(project, Localization.getString("exception.message.empty_folder"))
                    return
                }

                fileUtils.deleteFonts(resourcesUtil.fontDir) { path, filesCount ->
                    val location = " ${Localization.getString("notification.message.part.in").plus(" $path")}"

                    buildDeleteSuccessNotification(
                        project,
                        Localization.getString("notification.message.fonts_deleted", filesCount).plus(location)
                    )
                }
            }
        }
    }
}