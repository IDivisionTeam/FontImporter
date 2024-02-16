package team.idivision.plugin.font_importer.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import team.idivision.plugin.font_importer.actions.core.BaseAction
import team.idivision.plugin.font_importer.data.AndroidFileManager
import team.idivision.plugin.font_importer.dialog.importfont.ImportFontAgreement
import team.idivision.plugin.font_importer.dialog.importfont.ImportFontDialog
import team.idivision.plugin.font_importer.dialog.importfont.ImportFontPresenter
import team.idivision.plugin.font_importer.utils.files.FileUtils


class FontImportAction : BaseAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(event: AnActionEvent) {
        val currentProject: Project? = event.project
        val presenter: ImportFontAgreement.Presenter = initPresenter(currentProject)

        ImportFontDialog(currentProject, presenter).showAndGet()
    }

    private fun initPresenter(project: Project?): ImportFontAgreement.Presenter {
        val fileUtils = FileUtils(project)
        val fileManager = AndroidFileManager(project)
        return ImportFontPresenter(fileUtils, fileManager)
    }
}