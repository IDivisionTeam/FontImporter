package team.idivision.plugin.font_importer.localization

import org.jetbrains.annotations.PropertyKey
import java.text.MessageFormat
import java.util.*
import kotlin.math.abs


object Localization {
    private val bundle = ResourceBundle.getBundle("strings")

    fun getString(@PropertyKey(resourceBundle = "strings") key: String, vararg params: Any): String {
        val value = bundle.getString(key)

        if (params.isEmpty()) return value

        return MessageFormat.format(value, *params)
    }

    fun getPlural(value: Int, keyOne: String, keyOther: String): String =
        if (abs(value) == 1) getString(keyOne) else getString(keyOther)
}