package team.uptech.fontimporter.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import team.uptech.fontimporter.actions.core.BaseAction
import team.uptech.fontimporter.dialog.importfont.ImportFontAgreement
import team.uptech.fontimporter.dialog.importfont.ImportFontDialog
import team.uptech.fontimporter.dialog.importfont.ImportFontPresenter
import team.uptech.fontimporter.utils.files.FileUtils
import team.uptech.fontimporter.utils.files.ResourcesUtil


class FontImportAction : BaseAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentProject: Project? = event.project
        val presenter: ImportFontAgreement.Presenter = initPresenter(currentProject)

        ImportFontDialog(currentProject, presenter).showAndGet()
    }

    private fun initPresenter(project: Project?): ImportFontAgreement.Presenter {
        val fileUtils = FileUtils(project)
        val resUtils = ResourcesUtil(project)
        return ImportFontPresenter(fileUtils, resUtils)
    }
}