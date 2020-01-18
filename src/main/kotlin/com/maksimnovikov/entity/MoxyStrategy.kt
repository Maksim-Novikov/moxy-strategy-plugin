package com.maksimnovikov.entity

enum class MoxyStrategy(val className: String) {
    ADD_TO_END_SINGLE_STRATEGY("AddToEndSingleStrategy"),
    ADD_TO_END_SINGLE_TAG_STRATEGY("AddToEndSingleTagStrategy"),
    ONE_EXECUTION_STATE_STRATEGY("OneExecutionStateStrategy"),
    SKIP_STRATEGY("SkipStrategy"),
    ADD_TO_END_STRATEGY("AddToEndStrategy");

    override fun toString(): String = className

    companion object {
        const val ANNOTATION_NAME = "StateStrategyType"
        const val PATH = "moxy.viewstate.strategy."
    }
}
