package com.maksimnovikov.intention

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PriorityAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.maksimnovikov.entity.MoxyStrategy
import com.maksimnovikov.entity.StrategyIntentionType
import com.maksimnovikov.entity.isClassInheritMvpView
import com.maksimnovikov.entity.isHasMoxyAnnotation
import com.maksimnovikov.entity.replaceAnnotation
import com.maksimnovikov.extentions.getParentOfType
import com.maksimnovikov.extentions.showSelectPopup
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction


/**
 * Implements an com.maksimnovikov.intention action to replace current strategy for another
 *
 * @author dsl
 */
class MvpViewStrategyIntention : PsiElementBaseIntentionAction(), IntentionAction, PriorityAction {

    private val logger = Logger.getInstance("MvpViewStrategyIntention")
    /**
     * If this action is applicable, returns the text to be shown in the list of
     * com.maksimnovikov.intention actions available.
     */
    override fun getText(): String {
        return StrategyIntentionType.ReplaceStrategy.title
    }

    /**
     * Returns text for name of this family of intentions. It is used to externalize
     * "auto-show" state of intentions.
     * It is also the directory name for the descriptions.
     *
     * @see com.intellij.codeInsight.intention.IntentionManager.registerIntentionAndMetaData
     * @return  the com.maksimnovikov.intention family name.
     */
    override fun getFamilyName(): String {
        return "MvpViewStrategyIntention"
    }

    override fun isAvailable(
        project: Project,
        editor: Editor?,
        element: PsiElement
    ): Boolean {
        if ((element.containingFile ?: return false) !is KtFile) return false
        if (!element.isClassInheritMvpView()) return false
        // get parent function
        val ktFunction = element.getParentOfType<KtNamedFunction>() ?: return false
        return ktFunction.isHasMoxyAnnotation()
    }

    /**
     * Show selector dialog with default moxy strategies
     * after selection replace current StateStrategyType annotation
     *
     * @param  project   a reference to the Project object being edited.
     * @param  editor    a reference to the object editing the project source
     * @param  element   a reference to the PSI element currently under the caret
     * when manipulation of the psi tree fails.
     * @see MvpViewStrategyIntention.startInWriteAction
     */
    override fun invoke(
        project: Project,
        editor: Editor,
        element: PsiElement
    ) {
        editor.showSelectPopup(MoxyStrategy.values().toList()) { selectedStrategy ->
            WriteCommandAction.runWriteCommandAction(project) {
                val ktFunction = element.getParentOfType<KtNamedFunction>() ?: return@runWriteCommandAction
                ktFunction.replaceAnnotation(selectedStrategy, project, editor)
            }
        }
    }

    /**
     * Indicates this com.maksimnovikov.intention action expects the Psi framework to provide the write action
     * context for any changes.
     *
     * @return
     *  *  true if the com.maksimnovikov.intention requires a write action context to be provided
     *  *  false if this com.maksimnovikov.intention action will start a write action
     *
     */
    override fun startInWriteAction(): Boolean = true

    override fun getPriority(): PriorityAction.Priority = PriorityAction.Priority.TOP
}

