package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RightGap
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.listCellRenderer
import team.idivision.plugin.font_importer.dialog.ui.core.DropDownUi
import team.idivision.plugin.font_importer.localization.Localization
import javax.swing.DefaultComboBoxModel


class ModuleSelectionDropDown(
    private val items: Array<String>,
) : DropDownUi {
    private var selectedItem: String? = items.firstOrNull()

    override fun build(layout: Panel) {
        layout.row {
            label(Localization.getString("label.select_module"))
                .gap(RightGap.SMALL)

            comboBox(
                DefaultComboBoxModel(items),
                listCellRenderer {},
            ).bindItem(::selectedItem)
        }
    }

    override fun getSelectedItem(): String? = selectedItem
}