package team.idivision.plugin.font_importer.actions.core

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.buildExceptionNotification


abstract class BaseAction : AnAction() {

    protected fun showError(project: Project?, message: String? = null) {
        buildExceptionNotification(project, message ?: Localization.getString("exception.message.unknown"))
    }

    override fun update(event: AnActionEvent) {
        val project: Project? = event.project
        event.presentation.isEnabledAndVisible = project != null
    }
}