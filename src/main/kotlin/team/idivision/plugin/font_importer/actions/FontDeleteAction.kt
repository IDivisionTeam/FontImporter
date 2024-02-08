package team.idivision.plugin.font_importer.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import team.idivision.plugin.font_importer.actions.core.BaseAction
import team.idivision.plugin.font_importer.dialog.deletefont.DeleteFontAgreement
import team.idivision.plugin.font_importer.dialog.deletefont.DeleteFontDialog
import team.idivision.plugin.font_importer.dialog.deletefont.DeleteFontPresenter
import team.idivision.plugin.font_importer.utils.files.FileUtils
import team.idivision.plugin.font_importer.utils.files.ResourcesUtil


class FontDeleteAction : BaseAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(event: AnActionEvent) {
        val currentProject: Project? = event.project
        val presenter: DeleteFontAgreement.Presenter = initPresenter(currentProject)

        DeleteFontDialog(currentProject, presenter).showAndGet()
    }

    private fun initPresenter(project: Project?): DeleteFontAgreement.Presenter {
        val fileUtils = FileUtils(project)
        val resUtils = ResourcesUtil(project)
        return DeleteFontPresenter(fileUtils, resUtils)
    }
}