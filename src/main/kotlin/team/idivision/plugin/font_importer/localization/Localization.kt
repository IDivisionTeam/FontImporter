package team.idivision.plugin.font_importer.localization

import org.jetbrains.annotations.PropertyKey
import java.text.MessageFormat
import java.util.*


object Localization {
    private val bundle = ResourceBundle.getBundle("strings")

    fun getString(@PropertyKey(resourceBundle = "strings") key: String, vararg params: Any): String {
        val value = bundle.getString(key)

        if (params.isEmpty()) return value
        return MessageFormat.format(value, *params)
    }
}