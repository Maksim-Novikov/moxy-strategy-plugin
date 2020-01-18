package com.maksimnovikov.entity

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.maksimnovikov.extentions.findAnnotationByName
import com.maksimnovikov.extentions.findAttributeValue
import com.maksimnovikov.extentions.getParentOfType
import com.maksimnovikov.extentions.isHasAnnotation
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.idea.quickfix.moveCaretToEnd
import org.jetbrains.kotlin.idea.refactoring.fqName.getKotlinFqName
import org.jetbrains.kotlin.idea.util.addAnnotation
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction


fun PsiElement.isClassInheritMvpView(): Boolean {
    // get psi class
    val ktClass = getParentOfType<KtClass>() ?: return false
    // we need only interfaces
    if (!ktClass.isInterface()) return false
    // find moxy PsiCLass
    val baseClass =
        try {
            JavaPsiFacade.getInstance(project).findClass(
                "moxy.MvpView",
                GlobalSearchScope.allScope(project)
            ) ?: return false
        } catch (t: Throwable) {
            return false
        }
    // compare all super types with MvpView
    val isInheritor = ktClass.toLightClass()?.isInheritor(baseClass, true)
    return isInheritor == true
}

fun KtNamedFunction.isHasMoxyAnnotation() = isHasAnnotation(MoxyStrategy.ANNOTATION_NAME)

fun KtNamedFunction.addStrategyAnnotation(
    strategy: MoxyStrategy,
    project: Project,
    editor: Editor?,
    tagArgument: String? = null
) {
    var isMoveToTag = false
    val newTagArgument = when {
        strategy == MoxyStrategy.ADD_TO_END_SINGLE_TAG_STRATEGY && tagArgument == null -> {
            isMoveToTag = true
            ", tag = "
        }
        tagArgument != null -> ", tag = $tagArgument"
        else -> ""
    }
    addAnnotation(
        annotationFqName = FqName(MoxyStrategy.PATH + MoxyStrategy.ANNOTATION_NAME),
        annotationInnerText = MoxyStrategy.PATH + strategy.className + "::class" + newTagArgument,
        whiteSpaceText = "\n"
    )
    if (isMoveToTag) {
        val newAnnotation = findAnnotationByName(MoxyStrategy.ANNOTATION_NAME)
        val attributeValue = newAnnotation?.findAttributeValue("tag")
        (attributeValue as? PsiElement)?.moveCaretToEnd(editor, project)
    }
}

fun KtNamedFunction.replaceAnnotation(strategy: MoxyStrategy, project: Project, editor: Editor) {
    val isAlreadyHaveSameStrategy =
        annotationEntries.any { it.getKotlinFqName().toString().contains(strategy.className) }
    if (isAlreadyHaveSameStrategy) return
    val annotationToReplace = findAnnotationByName(MoxyStrategy.ANNOTATION_NAME)

    if (annotationToReplace == null) {
        addStrategyAnnotation(strategy, project, editor)
    } else {
        val tagArgument = annotationToReplace.findAttributeValue("tag")
            ?.getArgumentExpression()?.text
        annotationToReplace.delete()
        addStrategyAnnotation(strategy, project, editor, tagArgument)
    }
}