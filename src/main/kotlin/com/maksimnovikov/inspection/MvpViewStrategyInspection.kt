package com.maksimnovikov.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.maksimnovikov.entity.*
import com.maksimnovikov.extentions.getProjectEditor
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.namedFunctionVisitor

class MvpViewStrategyInspection : AbstractKotlinInspection() {
    private val fixes = MoxyStrategy.values().map { AddStrategyFix(it) }.toTypedArray()

    override fun getDisplayName(): String = StrategyIntentionType.MissingStrategy.title

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return namedFunctionVisitor { ktNamedFunction ->
            if (!ktNamedFunction.isClassInheritMvpView() ||
                ktNamedFunction.isHasMoxyAnnotation()
            ) return@namedFunctionVisitor
            holder.registerProblem(
                ktNamedFunction,
                StrategyIntentionType.MissingStrategy.title,
                *fixes
            )
        }
    }

    class AddStrategyFix(
        private val strategy: MoxyStrategy
    ) : LocalQuickFix {
        override fun getFamilyName(): String = "add ${strategy.className}"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val ktFunction = (descriptor.psiElement as KtNamedFunction)
            val editor = ktFunction.getProjectEditor()
            ktFunction.addStrategyAnnotation(strategy, project, editor)
        }
    }
}