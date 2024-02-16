package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.listCellRenderer
import javax.swing.DefaultComboBoxModel


fun Row.dropDown(
    label: String,
    items: Array<String>,
): Cell<ComboBox<String>> {
    return comboBox(
        DefaultComboBoxModel(items),
        listCellRenderer { text = it },
    )
        .applyToComponent {
            isFocusable = false
        }
        .label(label, LabelPosition.TOP)
}