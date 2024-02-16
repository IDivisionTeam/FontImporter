package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.Row
import team.idivision.plugin.font_importer.localization.Localization
import javax.swing.JButton


fun Row.fileChooser(action: () -> Unit): Cell<JButton> =
    button(Localization.getString("action.choose_fonts")) { action() }
        .applyToComponent { isFocusable = false }