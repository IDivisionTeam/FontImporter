package team.uptech.fontimporter.dialog.core

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.buildExceptionNotification


abstract class BaseDialog(protected val project: Project?) : DialogWrapper(project, true) {
    protected fun showError(project: Project?, message: String? = null) {
        buildExceptionNotification(project, message ?: Localization.getString("exception.message.unknown"))
    }
}