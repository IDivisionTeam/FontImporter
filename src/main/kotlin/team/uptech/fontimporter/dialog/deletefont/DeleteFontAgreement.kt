package team.uptech.fontimporter.dialog.deletefont

import team.uptech.fontimporter.dialog.core.BaseAgreement


interface DeleteFontAgreement {
    interface View : BaseAgreement.View {
        fun showNoResFolderError(module: String)
        fun showNoFontFolderError(module: String)
        fun showEmptyFontFolderError(module: String)
        fun showDeleteSuccess(filesCount: Int, location: String)
    }

    interface Presenter : BaseAgreement.Presenter<View> {
        fun getModuleDropDownItems(): Array<String>
        fun deleteFontsFromModule(module: String)
    }
}