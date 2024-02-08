package team.idivision.plugin.font_importer.actions.core

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project


abstract class BaseAction : AnAction() {
    override fun update(event: AnActionEvent) {
        val project: Project? = event.project
        event.presentation.isEnabledAndVisible = project != null
    }
}