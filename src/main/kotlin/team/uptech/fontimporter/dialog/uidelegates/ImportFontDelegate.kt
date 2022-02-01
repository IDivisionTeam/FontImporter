package team.uptech.fontimporter.dialog.uidelegates

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.PropertyBinding
import com.intellij.ui.layout.panel
import team.uptech.fontimporter.dialog.uidelegates.core.UiDelegate
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.files.ResourcesUtil
import java.awt.Dimension
import javax.swing.DefaultComboBoxModel


class ImportFontDelegate(
    resourcesUtil: ResourcesUtil,
    private val fileChooser: () -> Unit
) : UiDelegate() {

    private val moduleItems: Array<String> = resourcesUtil.getAvailableModuleNames().toTypedArray()
    private var selectedItem: String? = moduleItems.first()

    private val fontsTextBeforeRenaming = JBTextArea().apply {
        isEditable = false
        isFocusable = false
        preferredSize = Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT)
    }

    private val fontsTextAfterRenaming = JBTextArea().apply {
        isEditable = false
        isFocusable = false
        preferredSize = Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT)
    }

    private fun getModuleBinding() = PropertyBinding({ selectedItem }, { selectedItem = it })

    override fun buildDialogUi(): DialogPanel {
        return panel {
            row(separated = true) {
                cell(isFullWidth = true) {
                    label(Localization.getString("label.select_module"))
                    comboBox(DefaultComboBoxModel(moduleItems), getModuleBinding()).apply { constraints(CCFlags.growX) }
                }
            }
            row {
                cell(isFullWidth = true) {
                    this@panel.row {
                        label(Localization.getString("label.selected_fonts"))
                        label(Localization.getString("label.formatted_fonts"))
                    }
                }
                cell(isFullWidth = true) {
                    this@panel.row {
                        fontsTextBeforeRenaming()
                        fontsTextAfterRenaming()
                    }
                }
            }
            row {
                button(Localization.getString("action.choose_fonts")) { fileChooser() }
            }
        }.apply {
            preferredSize = Dimension(ROOT_WIDTH, ROOT_HEIGHT)
        }
    }

    override fun getDropDownSelectedItem(): String? = selectedItem

    override fun setTexts(vararg texts: String) {
        if (texts.isEmpty() || texts.size < 2) return

        fontsTextBeforeRenaming.text = texts[0]
        fontsTextAfterRenaming.text = texts[1]
    }

    companion object {
        private const val ROOT_WIDTH = 800
        private const val ROOT_HEIGHT = 350

        private const val TEXT_AREA_WIDTH = 300
        private const val TEXT_AREA_HEIGHT = 250
    }
}