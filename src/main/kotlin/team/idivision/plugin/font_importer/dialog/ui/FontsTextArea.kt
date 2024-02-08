package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RightGap
import com.intellij.ui.dsl.builder.bindText
import team.idivision.plugin.font_importer.dialog.ui.core.TextAreaUi
import team.idivision.plugin.font_importer.localization.Localization
import java.awt.Dimension


class FontsTextArea : TextAreaUi {

    private var fontsTextBeforeRenaming: String = ""
    private var fontsTextAfterRenaming: String = ""

    override fun build(layout: Panel) {
        layout.indent {
            layout.row {
                textArea()
                    .label(Localization.getString("label.selected_fonts"))
                    .applyToComponent {
                        isFocusable = false
                        isEditable = false
                        preferredSize = Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT)
                    }
                    .gap(RightGap.SMALL)
                    .bindText(::fontsTextBeforeRenaming)

                textArea()
                    .label(Localization.getString("label.formatted_fonts"))
                    .applyToComponent {
                        isFocusable = false
                        isEditable = false
                        preferredSize = Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT)
                    }
                    .bindText(::fontsTextAfterRenaming)
            }
        }
    }

    override fun setTexts(vararg texts: String) {
        if (texts.size < 2) return

        fontsTextBeforeRenaming = texts[0]
        fontsTextAfterRenaming = texts[1]
    }

    companion object {
        private const val TEXT_AREA_WIDTH = 300
        private const val TEXT_AREA_HEIGHT = 250
    }
}