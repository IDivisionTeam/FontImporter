package team.idivision.plugin.font_importer.dialog.core

interface BaseAgreement {
    interface View
    interface Presenter<V> {
        fun bindView(view: V)
    }
}