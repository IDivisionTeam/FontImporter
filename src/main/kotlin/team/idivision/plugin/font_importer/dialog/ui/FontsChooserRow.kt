package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.ui.layout.LayoutBuilder
import team.idivision.plugin.font_importer.dialog.ui.core.CoreUi
import team.idivision.plugin.font_importer.localization.Localization


class FontsChooserRow(private val fileChooser: () -> Unit) : CoreUi {

    override fun buildUi(layout: LayoutBuilder) {
        layout.row {
            button(Localization.getString("action.choose_fonts")) { fileChooser() }
        }
    }
}