package team.idivision.plugin.font_importer.dialog.core

abstract class BasePresenter<View : BaseAgreement.View>: BaseAgreement.Presenter<View> {
    protected var view: View? = null

    override fun bindView(view: View) {
        this.view = view
    }
}