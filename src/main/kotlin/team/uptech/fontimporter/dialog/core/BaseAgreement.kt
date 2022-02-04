package team.uptech.fontimporter.dialog.core

interface BaseAgreement {
    interface View
    interface Presenter<V> {
        fun bindView(view: V)
    }
}