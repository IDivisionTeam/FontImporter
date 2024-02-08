package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.ui.dsl.builder.Panel
import team.idivision.plugin.font_importer.dialog.ui.core.CoreUi
import team.idivision.plugin.font_importer.localization.Localization


class FontsChooserRow(private val fileChooser: () -> Unit) : CoreUi {

    override fun build(layout: Panel) {
        layout.row {
            button(Localization.getString("action.choose_fonts")) { fileChooser() }
        }
    }
}