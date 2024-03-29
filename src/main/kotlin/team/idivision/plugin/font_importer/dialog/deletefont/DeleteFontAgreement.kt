package team.idivision.plugin.font_importer.dialog.deletefont

import team.idivision.plugin.font_importer.dialog.core.BaseAgreement


interface DeleteFontAgreement {
    interface View : BaseAgreement.View {
        fun showNoResFolderError(module: String)
        fun showNoFontFolderError(module: String)
        fun showEmptyFontFolderError(module: String)
        fun showDeleteSuccess(filesCount: Int, location: String)
    }

    interface Presenter : BaseAgreement.Presenter<View> {
        fun getState(): DeleteFontsState

        fun getModuleDropDownItems(): Array<String>
        fun deleteFontsFromModule()
    }
}