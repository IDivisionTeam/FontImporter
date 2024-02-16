package team.idivision.plugin.font_importer.data

import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException


interface FileManager {
    @Throws(IOException::class)
    fun hasResourcesDir(moduleName: String): Boolean

    @Throws(IOException::class)
    fun createResDir(moduleName: String): VirtualFile?

    @Throws(IOException::class)
    fun hasFontDir(moduleName: String): Boolean

    @Throws(IOException::class)
    fun createFontDir(moduleName: String): VirtualFile?

    @Throws(IOException::class)
    fun findFontDir(moduleName: String): VirtualFile?

    fun getAvailableModuleNames(): Array<String>
}