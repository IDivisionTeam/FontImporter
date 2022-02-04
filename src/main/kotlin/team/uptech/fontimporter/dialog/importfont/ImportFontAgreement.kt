package team.uptech.fontimporter.dialog.importfont

import com.intellij.openapi.vfs.VirtualFile
import team.uptech.fontimporter.dialog.core.BaseAgreement


interface ImportFontAgreement {
    interface View : BaseAgreement.View {
        fun showNoResFolderError(module: String)
        fun showNoFontFolderError(module: String)
        fun showImportSuccess(filesCount: Int, replacedFilesCount: Int, path: String)
        fun updateDialogUi(fontsBeforeRenaming: String, fontsAfterRenaming: String)
    }

    interface Presenter : BaseAgreement.Presenter<View> {
        fun getModuleDropDownItems(): Array<String>
        fun saveSelectedFonts(selectedFonts: List<VirtualFile>)
        fun importSelectedFontToModule(module: String)
    }
}