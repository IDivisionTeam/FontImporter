package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.listCellRenderer
import team.idivision.plugin.font_importer.dialog.ui.core.DropDownUi
import team.idivision.plugin.font_importer.localization.Localization
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.DefaultComboBoxModel


class DeleteFontsDropDown(
    private val items: Array<String>,
    private val boxListener: (String?) -> Unit
) : DropDownUi, ActionListener {
    private var selectedItem: String? = null

    override fun build(layout: Panel) {
        layout.row {
            label(Localization.getString("dialog.message.delete"))
            comboBox(
                DefaultComboBoxModel(items),
                listCellRenderer {},
            ).applyToComponent { addActionListener(this@DeleteFontsDropDown) }
                .bindItem(::selectedItem)
        }
    }

    override fun getSelectedItem(): String? = selectedItem

    override fun actionPerformed(event: ActionEvent?) {
        if (event?.source !is ComboBox<*>) return
        boxListener(selectedItem)
    }
}