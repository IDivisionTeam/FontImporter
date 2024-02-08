package team.idivision.plugin.font_importer.dialog.importfont

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.Panel
import team.idivision.plugin.font_importer.dialog.core.BaseDialog
import team.idivision.plugin.font_importer.dialog.ui.FontsChooserRow
import team.idivision.plugin.font_importer.dialog.ui.FontsTextArea
import team.idivision.plugin.font_importer.dialog.ui.ModuleSelectionDropDown
import team.idivision.plugin.font_importer.dialog.ui.core.CoreUi
import team.idivision.plugin.font_importer.dialog.ui.core.DropDownUi
import team.idivision.plugin.font_importer.dialog.ui.core.TextAreaUi
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.buildImportSuccessNotification
import team.idivision.plugin.font_importer.utils.files.ResourcesUtil
import java.io.IOException


class ImportFontDialog(
    project: Project?,
    presenter: ImportFontAgreement.Presenter
) : BaseDialog<ImportFontAgreement.View, ImportFontAgreement.Presenter>(project, presenter), ImportFontAgreement.View {

    private lateinit var moduleSelectionDropDown: DropDownUi
    private lateinit var fontsTextArea: TextAreaUi
    private lateinit var fontsChooserRow: CoreUi

    init {
        title = Localization.getString("dialog.title.import")
        setOKButtonText(Localization.getString("action.import"))
        setCancelButtonText(Localization.getString("action.cancel"))

        isOKActionEnabled = false
        init()

        presenter.bindView(this)
    }

    override fun buildBody(layout: Panel) {
        moduleSelectionDropDown = ModuleSelectionDropDown(presenter.getModuleDropDownItems())
            .also { it.build(layout) }
        fontsTextArea = FontsTextArea()
            .also { it.build(layout) }
        fontsChooserRow = FontsChooserRow { openFileChooserDialog() }
            .also { it.build(layout) }
    }

    private fun openFileChooserDialog() {
        val descriptor = FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor()
        FileChooser.chooseFiles(descriptor, project, null) { selectedFonts ->
            presenter.saveSelectedFonts(selectedFonts)
        }
    }

    override fun updateDialogUi(fontsBeforeRenaming: String, fontsAfterRenaming: String) {
        dialog.apply()
        fontsTextArea.setTexts(fontsBeforeRenaming, fontsAfterRenaming)
        isOKActionEnabled = true
    }

    override fun doOKAction() {
        super.doOKAction()
        try {
            val module = moduleSelectionDropDown.getSelectedItem() ?: ResourcesUtil.DEFAULT_MODULE
            presenter.importSelectedFontToModule(module)
        } catch (error: IOException) {
            showError(project, error.message)
        }
    }

    override fun showNoResFolderError(module: String) {
        showError(project, Localization.getString("exception.message.no_res_folder", module))
    }

    override fun showNoFontFolderError(module: String) {
        showError(project, Localization.getString("exception.message.no_font_folder", module))
    }

    override fun showImportSuccess(filesCount: Int, replacedFilesCount: Int, path: String) {
        buildImportSuccessNotification(project, filesCount, replacedFilesCount, path)
    }

    override fun dialogWidth(): Int = 800
    override fun dialogHeight(): Int = 350
}