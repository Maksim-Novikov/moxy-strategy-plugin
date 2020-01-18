package com.maksimnovikov.extentions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory

fun <T> Editor.showSelectPopup(
    strategies: List<T>,
    onSelected: (T) -> Unit
) {
    JBPopupFactory.getInstance()
        .createPopupChooserBuilder(strategies)
        .setRequestFocus(true)
        .setCancelOnClickOutside(true)
        .setItemChosenCallback { onSelected(it) }
        .createPopup()
        .showInBestPositionFor(this)
}