package team.idivision.plugin.font_importer.utils.extensions

import com.intellij.openapi.vfs.VirtualFile


fun VirtualFile?.toSimpleUrl(delimiter: String = "/"): String {
    if (this == null) return ""

    return this.presentableUrl.split(delimiter).takeLast(5).joinToString(delimiter)
}