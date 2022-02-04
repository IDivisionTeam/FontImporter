package team.uptech.fontimporter.dialog.ui

import com.intellij.ui.components.JBTextArea
import com.intellij.ui.layout.LayoutBuilder
import team.uptech.fontimporter.dialog.ui.core.TextAreaUi
import team.uptech.fontimporter.localization.Localization
import java.awt.Dimension


class FontsTextArea : TextAreaUi {

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

    override fun buildUi(layout: LayoutBuilder) {
        layout.row {
            cell(isFullWidth = true) {
                layout.row {
                    label(Localization.getString("label.selected_fonts"))
                    label(Localization.getString("label.formatted_fonts"))
                }
            }
            cell(isFullWidth = true) {
                layout.row {
                    fontsTextBeforeRenaming()
                    fontsTextAfterRenaming()
                }
            }
        }
    }

    override fun setTexts(vararg texts: String) {
        if (texts.isEmpty() || texts.size < 2) return

        fontsTextBeforeRenaming.text = texts[0]
        fontsTextAfterRenaming.text = texts[1]
    }

    companion object {
        private const val TEXT_AREA_WIDTH = 300
        private const val TEXT_AREA_HEIGHT = 250
    }
}