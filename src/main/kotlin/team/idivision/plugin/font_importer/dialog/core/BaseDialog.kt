package team.idivision.plugin.font_importer.dialog.core

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.panel
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.buildExceptionNotification
import java.awt.Dimension
import javax.swing.JComponent


abstract class BaseDialog<View : BaseAgreement.View, Presenter : BaseAgreement.Presenter<View>>(
    protected val project: Project?, protected val presenter: Presenter
) : DialogWrapper(project, true) {

    protected lateinit var dialog: DialogPanel

    protected fun showError(project: Project?, message: String? = null) {
        buildExceptionNotification(project, message ?: Localization.getString("exception.message.unknown"))
    }

    abstract fun buildBody(layout: Panel)

    override fun createCenterPanel(): JComponent {
        return panel { buildBody(this) }
            .apply {
                if (width == DELEGATE_TO_PARENT || height == DELEGATE_TO_PARENT) {
                    return this
                }

                preferredSize = Dimension(dialogWidth(), dialogHeight())
            }
            .also {
                dialog = it
            }
    }

    open fun dialogWidth(): Int = DELEGATE_TO_PARENT
    open fun dialogHeight(): Int = DELEGATE_TO_PARENT

    companion object {
        const val DELEGATE_TO_PARENT = -1
    }
}