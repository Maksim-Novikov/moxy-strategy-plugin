package com.maksimnovikov.extentions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

inline fun <reified T : PsiElement> PsiElement.getParentOfType(): T? {
    return PsiTreeUtil.getParentOfType(this, T::class.java)
}

fun PsiElement.getProjectEditor(): Editor? {
    val fileEditor = FileEditorManager.getInstance(project).getSelectedEditor(containingFile.virtualFile)
    return (fileEditor as? TextEditor)?.editor
}