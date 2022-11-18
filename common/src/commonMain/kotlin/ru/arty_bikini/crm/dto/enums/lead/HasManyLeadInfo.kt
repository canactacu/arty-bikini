package ru.arty_bikini.crm.dto.enums.lead

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//категория (табл лидов)
enum class HasManyLeadInfo (override val displayName: String) : HasDisplayValue {
    A("А"),
    B("В"),
    C("С")
}