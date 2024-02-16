package team.idivision.plugin.font_importer.dialog.ui

import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.rows
import com.intellij.util.ui.JBDimension


fun Row.readOnlyTextArea(
    label: String,
): Cell<JBTextArea> {
    return textArea()
        .label(label, LabelPosition.TOP)
        .rows(10)
        .applyToComponent {
            isFocusable = false
            isEditable = false
            minimumSize = JBDimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT)
        }
}

private const val TEXT_AREA_WIDTH = 200
private const val TEXT_AREA_HEIGHT = 250