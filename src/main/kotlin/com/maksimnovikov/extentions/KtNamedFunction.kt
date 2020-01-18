package com.maksimnovikov.extentions

import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtNamedFunction

fun KtNamedFunction.findAnnotationByName(annotationName: String): KtAnnotationEntry? =
    annotationEntries.firstOrNull { it.shortName.toString() == annotationName }

fun KtNamedFunction.isHasAnnotation(annotationName: String): Boolean =
    annotationEntries.any { it.shortName.toString() == annotationName }
