package team.idivision.plugin.font_importer.dialog.importfont

import com.intellij.openapi.vfs.VirtualFile
import team.idivision.plugin.font_importer.dialog.core.BaseAgreement


interface ImportFontAgreement {
    interface View : BaseAgreement.View {
        fun showNoResFolderError(module: String)
        fun showNoFontFolderError(module: String)
        fun showImportSuccess(filesCount: Int, replacedFilesCount: Int, path: String)
        fun updateTextFields()
        fun toggleActionButtonState(isEnabled: Boolean)
    }

    interface Presenter : BaseAgreement.Presenter<View> {
        fun getState(): ImportFontState

        fun getModuleDropDownItems(): Array<String>
        fun saveSelectedFonts(selectedFonts: List<VirtualFile>)
        fun importSelectedFontToModule()
    }
}