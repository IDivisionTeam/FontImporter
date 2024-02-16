package team.idivision.plugin.font_importer.dialog.importfont

import com.intellij.codeInspection.javaDoc.JavadocUIUtil.bindItem
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.*
import team.idivision.plugin.font_importer.dialog.core.BaseDialog
import team.idivision.plugin.font_importer.dialog.ui.dropDown
import team.idivision.plugin.font_importer.dialog.ui.fileChooser
import team.idivision.plugin.font_importer.dialog.ui.readOnlyTextArea
import team.idivision.plugin.font_importer.localization.Localization
import team.idivision.plugin.font_importer.utils.buildImportSuccessNotification
import java.io.IOException


class ImportFontDialog(
    project: Project?,
    presenter: ImportFontAgreement.Presenter,
) : BaseDialog<ImportFontAgreement.View, ImportFontAgreement.Presenter>(project, presenter), ImportFontAgreement.View {

    private var selectedFontsField: Cell<JBTextArea>? = null
    private var formattedFontsField: Cell<JBTextArea>? = null

    init {
        title = Localization.getString("dialog.title.import")
        setOKButtonText(Localization.getString("action.import"))
        setCancelButtonText(Localization.getString("action.cancel"))

        toggleActionButtonState(isEnabled = false)
        init()

        presenter.bindView(this)
    }

    override fun buildBody(layout: Panel) {
        layout.apply {
            rowsRange {
                row {
                    dropDown(
                        label = Localization.getString("label.select_module"),
                        items = presenter.getModuleDropDownItems(),
                    )
                        .align(Align.FILL)
                        .bindItem(presenter.getState()::selectedModule)
                }
                    .bottomGap(BottomGap.MEDIUM)

                row {
                    fileChooser { openFileChooserDialog() }
                }
                    .bottomGap(BottomGap.SMALL)

                row {
                    readOnlyTextArea(
                        label = Localization.getString("label.selected_fonts"),
                    )
                        .align(AlignX.FILL)
                        .also { selectedFontsField = it }
                }
                row {
                    readOnlyTextArea(
                        label = Localization.getString("label.formatted_fonts"),
                    )
                        .align(AlignX.FILL)
                        .also { formattedFontsField = it }
                }
            }
        }
    }

    private fun openFileChooserDialog() {
        val descriptor = FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor()

        FileChooser.chooseFiles(descriptor, project, null) { selectedFonts ->
            presenter.saveSelectedFonts(selectedFonts)
        }
    }

    override fun updateTextFields() {
        val state = presenter.getState()

        selectedFontsField?.text(state.selectedFonts)
        formattedFontsField?.text(state.formattedFonts)
    }

    override fun toggleActionButtonState(isEnabled: Boolean) {
        isOKActionEnabled = isEnabled
    }

    override fun doOKAction() {
        try {
            presenter.importSelectedFontToModule()
        } catch (error: IOException) {
            showError(project, error.message)
        }
        super.doOKAction()
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