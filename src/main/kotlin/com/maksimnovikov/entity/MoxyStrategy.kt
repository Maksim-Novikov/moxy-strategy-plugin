package com.maksimnovikov.entity

enum class MoxyStrategy(val className: String, val shortName: String?) {
    ADD_TO_END_SINGLE_STRATEGY("AddToEndSingleStrategy", "AddToEndSingle"),
    ADD_TO_END_SINGLE_TAG_STRATEGY("AddToEndSingleTagStrategy", null),
    ONE_EXECUTION_STATE_STRATEGY("OneExecutionStateStrategy", "OneExecution"),
    SKIP_STRATEGY("SkipStrategy", "Skip"),
    ADD_TO_END_STRATEGY("AddToEndStrategy", "AddToEnd");

    override fun toString(): String = className

    companion object {
        /**
         * @return short annotation names + full annotation name
         * */
        fun annotationNames(): List<String> = values().mapNotNull { it.shortName } + ANNOTATION_NAME

        const val ANNOTATION_NAME = "StateStrategyType"
        const val PATH = "moxy.viewstate.strategy."
        const val ALIAS_PATH = "moxy.viewstate.strategy.alias."
    }
}
