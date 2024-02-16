package team.idivision.plugin.font_importer.data

import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.ResourceFolderManager
import team.idivision.plugin.font_importer.data.directory.Dir


internal class AndroidFileManager(private val project: Project?) : FileManager {
    private val facets: Map<String, AndroidFacet> = getAvailableFacets(project)
        .associateBy { facet ->
            facet.module.name.split('.').drop(1)[0]
        }

    override fun getAvailableModuleNames(): Array<String> {
        if (project == null) return emptyArray()
        return facets.keys.toTypedArray()
    }

    override fun hasResourcesDir(moduleName: String): Boolean =
        getResourcesDir(moduleName) != null

    override fun createResDir(moduleName: String): VirtualFile? =
        createDir(moduleName, Dir.Res)

    override fun hasFontDir(moduleName: String): Boolean =
        findFontDir(moduleName) != null

    override fun createFontDir(moduleName: String): VirtualFile? =
        createDir(moduleName, Dir.Font)

    override fun findFontDir(moduleName: String): VirtualFile? =
        getResourcesDir(moduleName)?.findChild(Dir.Font.name)

    private fun createDir(moduleName: String, dir: Dir): VirtualFile? {
        var file: VirtualFile? = null

        WriteCommandAction.runWriteCommandAction(project) {
            file = getResourcesDir(moduleName)?.createChildDirectory(project, dir.name)
        }

        return file
    }


    private fun getResourcesDir(moduleName: String): VirtualFile? {
        val facet = facets[moduleName] ?: return null
        return ResourceFolderManager.getInstance(facet).folders.firstOrNull()
    }

    /** Returns Android modules available in the project.
     *
     * Only `src.main` build type included.
     */
    private fun getAvailableFacets(project: Project?): List<AndroidFacet> {
        if (project == null) return emptyList()

        return ProjectFacetManager.getInstance(project).getFacets(AndroidFacet.ID)
            .filter { it.module.name.contains("main") }
    }
}