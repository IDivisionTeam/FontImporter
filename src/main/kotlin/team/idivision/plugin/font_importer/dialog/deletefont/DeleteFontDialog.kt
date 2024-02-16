package team.idivision.plugin.font_importer.dialog.deletefont

import com.intellij.codeInspection.javaDoc.JavadocUIUtil.bindItem
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.Panel
import team.idivision.plugin.font_importer.dialog.core.BaseDialog
import team.idivision.plugin.font_importer.dialog.ui.dropDown
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.buildDeleteSuccessNotification
import java.io.IOException


class DeleteFontDialog(
    project: Project?,
    presenter: DeleteFontAgreement.Presenter
) : BaseDialog<DeleteFontAgreement.View, DeleteFontAgreement.Presenter>(project, presenter), DeleteFontAgreement.View {

    init {
        title = Localization.getString("dialog.title.delete")
        setOKButtonText(Localization.getString("action.delete"))
        setCancelButtonText(Localization.getString("action.cancel"))
        init()
        presenter.bindView(this)
    }

    override fun buildBody(layout: Panel) {
        layout.apply {
            row {
                dropDown(
                    label = Localization.getString("dialog.message.delete"),
                    items = presenter.getModuleDropDownItems(),
                )
                    .align(Align.FILL)
                    .bindItem(presenter.getState()::selectedModule)
            }
        }
    }

    override fun doOKAction() {
        try {
            presenter.deleteFontsFromModule()
        } catch (error: IOException) {
            showError(project, error.message)
        }
        super.doOKAction()
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

    override fun dialogWidth(): Int = 500
    override fun dialogHeight(): Int = 30
}