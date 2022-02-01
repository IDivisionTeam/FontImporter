package team.uptech.fontimporter.dialog.uidelegates

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.panel
import team.uptech.fontimporter.dialog.uidelegates.core.UiDelegate
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.files.ResourcesUtil
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener


class DeleteFontDelegate(
    resourcesUtil: ResourcesUtil,
    private val actionListener: (selectedItem: String?) -> Unit
) : UiDelegate(), ActionListener {

    private val moduleItems: Array<String> = resourcesUtil.getAvailableModuleNames().toTypedArray()
    private var selectedItem: String? = null

    private val comboBox = ComboBox(moduleItems).apply {
        this.renderer = renderer ?: SimpleListCellRenderer.create("") { it.toString() }
        selectedItem = null
        addActionListener(this@DeleteFontDelegate)
    }

    override fun buildDialogUi(): DialogPanel {
        return panel {
            row {
                cell(isFullWidth = true) {
                    label(Localization.getString("dialog.message.delete"))
                    comboBox().apply {
                        constraints(CCFlags.growX)
                    }
                }
            }
        }.apply {
            preferredSize = Dimension(ROOT_WIDTH, ROOT_HEIGHT)
        }
    }

    override fun getDropDownSelectedItem(): String? = selectedItem

    override fun actionPerformed(event: ActionEvent?) {
        val box = (event?.source as? ComboBox<*>) ?: return

        selectedItem = box.selectedItem as? String?
        actionListener(selectedItem)
    }


    companion object {
        private const val ROOT_WIDTH = 500
        private const val ROOT_HEIGHT = 30
    }
}