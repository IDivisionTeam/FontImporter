package team.uptech.fontimporter.dialog.deletefont

import com.intellij.openapi.project.Project
import com.intellij.ui.layout.LayoutBuilder
import team.uptech.fontimporter.dialog.core.BaseDialog
import team.uptech.fontimporter.dialog.ui.DeleteFontsDropDown
import team.uptech.fontimporter.dialog.ui.core.DropDownUi
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.buildDeleteSuccessNotification
import team.uptech.fontimporter.utils.files.ResourcesUtil
import java.io.IOException


class DeleteFontDialog(
    project: Project?,
    presenter: DeleteFontAgreement.Presenter
) : BaseDialog<DeleteFontAgreement.View, DeleteFontAgreement.Presenter>(project, presenter), DeleteFontAgreement.View {

    private val dropDownRow: DropDownUi = DeleteFontsDropDown(presenter.getModuleDropDownItems()) { selectedItem ->
        isOKActionEnabled = !selectedItem.isNullOrBlank()
    }

    init {
        title = Localization.getString("dialog.title.delete")
        setOKButtonText(Localization.getString("action.delete"))
        setCancelButtonText(Localization.getString("action.cancel"))
        isOKActionEnabled = false
        init()
        presenter.bindView(this)
    }

    override fun buildBody(layoutBuilder: LayoutBuilder) {
        dropDownRow.buildUi(layoutBuilder)
    }

    override fun doOKAction() {
        super.doOKAction()
        try {
            val module = dropDownRow.getDropDownSelectedItem() ?: ResourcesUtil.DEFAULT_MODULE
            presenter.deleteFontsFromModule(module)
        } catch (error: IOException) {
            showError(project, error.message)
        }
    }

    override fun showNoResFolderError(module: String) {
        showError(project, Localization.getString("exception.message.no_res_folder", module))
    }

    override fun showNoFontFolderError(module: String) {
        showError(project, Localization.getString("exception.message.no_font_folder", module))
    }

    override fun showEmptyFontFolderError(module: String) {
        showError(project, Localization.getString("exception.message.empty_folder"))
    }

    override fun showDeleteSuccess(filesCount: Int, location: String) {
        buildDeleteSuccessNotification(
            project,
            Localization.getString("notification.message.fonts_deleted", filesCount).plus(location)
        )
    }

    override fun dialogWidth(): Int {
        return 500
    }

    override fun dialogHeight(): Int {
        return 30
    }
}