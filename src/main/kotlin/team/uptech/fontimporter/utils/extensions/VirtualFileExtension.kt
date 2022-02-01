package team.uptech.fontimporter.utils.extensions

import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.kotlin.idea.util.string.joinWithEscape


fun VirtualFile?.toSimpleUrl(delimiter: Char = '/'): String {
    if (this == null) return ""

    return this.presentableUrl.split(delimiter).takeLast(5).joinWithEscape(delimiter)
}