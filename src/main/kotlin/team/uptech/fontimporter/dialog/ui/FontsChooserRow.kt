package team.uptech.fontimporter.dialog.ui

import com.intellij.ui.layout.LayoutBuilder
import team.uptech.fontimporter.dialog.ui.core.CoreUi
import team.uptech.fontimporter.localization.Localization


class FontsChooserRow(private val fileChooser: () -> Unit) : CoreUi {

    override fun buildUi(layout: LayoutBuilder) {
        layout.row {
            button(Localization.getString("action.choose_fonts")) { fileChooser() }
        }
    }
}