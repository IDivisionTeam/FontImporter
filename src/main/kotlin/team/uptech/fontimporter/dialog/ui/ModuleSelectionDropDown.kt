package team.uptech.fontimporter.dialog.ui

import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.LayoutBuilder
import com.intellij.ui.layout.PropertyBinding
import team.uptech.fontimporter.dialog.ui.core.DropDownUi
import team.uptech.fontimporter.localization.Localization
import javax.swing.DefaultComboBoxModel


class ModuleSelectionDropDown(
    private val items: Array<String>
) : DropDownUi {

    private var selectedItem: String? = items.first()

    private fun getModuleBinding() = PropertyBinding({ selectedItem }, { selectedItem = it })

    override fun buildUi(layout: LayoutBuilder) {
        layout.row(separated = true) {
            cell(isFullWidth = true) {
                label(Localization.getString("label.select_module"))
                comboBox(DefaultComboBoxModel(items), getModuleBinding())
                    .apply { constraints(CCFlags.growX) }
            }
        }
    }

    override fun getDropDownSelectedItem(): String? = selectedItem
}