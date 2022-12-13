package ru.arty_bikini.crm.dto.enums

enum class CalcPresetRuleType(override val displayName: String) : HasDisplayValue {
    COST_PERCENT("% от цены"),
    COST_AMOUNT("на сумму"),
    COUNT("штук")
}