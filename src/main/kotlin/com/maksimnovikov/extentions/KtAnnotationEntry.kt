package com.maksimnovikov.extentions

import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.ValueArgument

fun KtAnnotationEntry.findAttributeValue(attributeName: String): ValueArgument? {
    return valueArguments.find {
        it.getArgumentName()?.asName?.identifier == attributeName
    }
}