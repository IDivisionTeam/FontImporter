package team.uptech.fontimporter.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import team.uptech.fontimporter.actions.core.BaseAction
import team.uptech.fontimporter.dialog.deletefont.DeleteFontAgreement
import team.uptech.fontimporter.dialog.deletefont.DeleteFontDialog
import team.uptech.fontimporter.dialog.deletefont.DeleteFontPresenter
import team.uptech.fontimporter.utils.files.FileUtils
import team.uptech.fontimporter.utils.files.ResourcesUtil


class FontDeleteAction : BaseAction() {

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