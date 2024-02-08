package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.LayoutBuilder
import team.idivision.plugin.font_importer.dialog.ui.core.DropDownUi
import team.idivision.plugin.font_importer.localization.Localization
import java.awt.event.ActionEvent
import java.awt.event.ActionListener


class DeleteFontsDropDown(
    items: Array<String>,
    private val boxListener: (String?) -> Unit
) : DropDownUi, ActionListener {

    private var selectedItem: String? = null

    private val comboBox = ComboBox(items).apply {
        this.renderer = renderer ?: SimpleListCellRenderer.create("") { it.toString() }
        selectedItem = null
        addActionListener(this@DeleteFontsDropDown)
    }

    override fun buildUi(layout: LayoutBuilder) {
        layout.row {
            cell(isFullWidth = true) {
                label(Localization.getString("dialog.message.delete"))
                comboBox().apply { constraints(CCFlags.growX) }
            }
        }
    }

    override fun getDropDownSelectedItem(): String? = selectedItem

    override fun actionPerformed(event: ActionEvent?) {
        val box = (event?.source as? ComboBox<*>) ?: return

        selectedItem = box.selectedItem as? String?
        boxListener(selectedItem)
    }
}