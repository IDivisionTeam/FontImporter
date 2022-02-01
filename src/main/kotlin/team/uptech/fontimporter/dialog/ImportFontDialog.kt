package team.uptech.fontimporter.dialog

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import team.uptech.fontimporter.dialog.core.BaseDialog
import team.uptech.fontimporter.dialog.uidelegates.ImportFontDelegate
import team.uptech.fontimporter.dialog.uidelegates.core.UiDelegate
import team.uptech.fontimporter.localization.Localization
import team.uptech.fontimporter.utils.buildImportSuccessNotification
import team.uptech.fontimporter.utils.files.FileUtils
import team.uptech.fontimporter.utils.files.ResourcesStatus
import team.uptech.fontimporter.utils.files.ResourcesUtil
import java.io.IOException
import javax.swing.JComponent


class ImportFontDialog(project: Project?) : BaseDialog(project) {

    private val fileUtils: FileUtils = FileUtils(project)
    private val resourcesUtil: ResourcesUtil = ResourcesUtil(project)

    private val filesSelected: MutableList<VirtualFile> = mutableListOf()
    private val delegate: UiDelegate = ImportFontDelegate(resourcesUtil) {
        openFileChooserDialog()
    }

    init {
        title = Localization.getString("dialog.title.import")
        setOKButtonText(Localization.getString("action.import"))
        setCancelButtonText(Localization.getString("action.cancel"))

        isOKActionEnabled = false
        init()
    }

    override fun createCenterPanel(): JComponent {
        return delegate.buildDialogUi()
    }

    private fun openFileChooserDialog() {
        val descriptor = FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor()
        FileChooser.chooseFiles(descriptor, project, null) { selectedFonts ->
            saveSelectedFonts(selectedFonts)
            updateDialogUi()
        }
    }

    private fun saveSelectedFonts(selectedFonts: List<VirtualFile>) {
        if (filesSelected.isNotEmpty()) filesSelected.clear()
        filesSelected.addAll(selectedFonts)
    }

    private fun updateDialogUi() {
        val fontsBeforeRenaming = filesSelected.joinToString(separator = "\n") { font -> font.nameWithoutExtension }
        val fontsAfterRenaming = fileUtils.cleanFileNames(filesSelected).joinToString(separator = "\n")

        delegate.setTexts(fontsBeforeRenaming, fontsAfterRenaming)

        isOKActionEnabled = true
    }

    override fun doOKAction() {
        super.doOKAction()
        try {
            importSelectedFontToModule()
        } catch (error: IOException) {
            showError(project, error.message)
        }
    }

    private fun importSelectedFontToModule() {
        val module = delegate.getDropDownSelectedItem() ?: ResourcesUtil.DEFAULT_MODULE

        when (resourcesUtil.createAndGetFontDir(module)) {
            ResourcesStatus.NoResDir -> {
                showError(project, Localization.getString("exception.message.no_res_folder", module))
            }
            ResourcesStatus.NoFontDir -> {
                showError(project, Localization.getString("exception.message.no_font_folder", module))
            }
            ResourcesStatus.Success -> {
                fileUtils.copyFonts(resourcesUtil.fontDir, filesSelected) { path, filesCount, replacedFilesCount ->
                    buildImportSuccessNotification(project, filesCount, replacedFilesCount, path)
                }
            }
        }
    }
}