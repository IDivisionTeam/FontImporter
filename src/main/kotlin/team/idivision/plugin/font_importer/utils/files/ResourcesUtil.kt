package team.idivision.plugin.font_importer.utils.files

import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.ResourceFolderManager
import java.io.IOException


class ResourcesUtil(private val project: Project?) {
    private val facets: List<AndroidFacet> = getAvailableFacets()

    var fontDir: VirtualFile? = null
        private set

    @Throws(IOException::class)
    fun createAndGetFontDir(moduleName: String = DEFAULT_MODULE): ResourcesStatus {
        if (!hasResDir(moduleName)) return ResourcesStatus.NoResDir

        if (hasFontDir(moduleName)) {
            fontDir = getFontDirRoot(moduleName)
        } else {
            WriteCommandAction.runWriteCommandAction(project) {
                fontDir = createFontDirRoot(moduleName)
            }
        }

        return if (fontDir == null) {
            ResourcesStatus.NoFontDir
        } else {
            ResourcesStatus.Success
        }
    }

    @Throws(IOException::class)
    fun getFontDir(moduleName: String = DEFAULT_MODULE): ResourcesStatus {
        if (!hasResDir(moduleName)) return ResourcesStatus.NoResDir
        if (!hasFontDir(moduleName)) return ResourcesStatus.NoFontDir

        fontDir = getFontDirRoot(moduleName)
        return ResourcesStatus.Success
    }

    private fun getResourceDirectories(moduleName: String): VirtualFile? {
        val currentFacet: AndroidFacet = getFacet(moduleName) ?: return null
        val allResourceDirectories = ResourceFolderManager.getInstance(currentFacet).folders

        return allResourceDirectories.firstOrNull()
    }

    @Throws(IOException::class)
    private fun getFontDirRoot(moduleName: String): VirtualFile? {
        val root: VirtualFile? = getResourceDirectories(moduleName)
        return root?.findChild(RES_FOLDER_FONT_NAME)
    }

    @Throws(IOException::class)
    private fun createFontDirRoot(moduleName: String): VirtualFile? {
        val root: VirtualFile? = getResourceDirectories(moduleName)
        return root?.createChildDirectory(project, RES_FOLDER_FONT_NAME)
    }

    @Throws(IOException::class)
    fun hasResDir(moduleName: String): Boolean {
        return getResourceDirectories(moduleName) != null
    }

    @Throws(IOException::class)
    fun hasFontDir(moduleName: String): Boolean {
        return getFontDirRoot(moduleName) != null
    }

    fun hasFonts(): Boolean {
        return !fontDir?.children.isNullOrEmpty()
    }

    private fun getFacet(moduleName: String): AndroidFacet? {
        return facets.firstOrNull { facet ->
            facet.getPresentableModuleName() == moduleName
        }
    }

    private fun getAvailableFacets(): List<AndroidFacet> {
        if (project == null) return emptyList()

        return ProjectFacetManager.getInstance(project).getFacets(AndroidFacet.ID)
    }

    fun getAvailableModuleNames(): List<String> {
        if (project == null) return emptyList()

        return facets.map { facet -> facet.getPresentableModuleName() }
    }

    private fun AndroidFacet.getPresentableModuleName(): String {
        return this.module.name.split('.').drop(1)[0]
    }

    companion object {
        private const val RES_FOLDER_FONT_NAME = "font"
        const val DEFAULT_MODULE = "app"
    }
}