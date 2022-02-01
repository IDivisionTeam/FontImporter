package team.uptech.fontimporter.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import team.uptech.fontimporter.actions.core.BaseAction
import team.uptech.fontimporter.dialog.ImportFontDialog


class FontImportAction : BaseAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentProject: Project? = event.project
        ImportFontDialog(currentProject).showAndGet()
    }
}