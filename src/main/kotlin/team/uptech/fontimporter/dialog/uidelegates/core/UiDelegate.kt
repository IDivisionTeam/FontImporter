package team.uptech.fontimporter.dialog.uidelegates.core

import com.intellij.openapi.ui.DialogPanel


abstract class UiDelegate {
    abstract fun buildDialogUi(): DialogPanel

    open fun setTexts(vararg texts: String) {
        /* Implement in child */
    }

    open fun getDropDownSelectedItem(): String? = null
}