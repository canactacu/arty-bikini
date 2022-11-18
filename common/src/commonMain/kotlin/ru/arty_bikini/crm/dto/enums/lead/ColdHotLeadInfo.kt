package ru.arty_bikini.crm.dto.enums.lead

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//холодный,теплый горячий
enum class ColdHotLeadInfo (override val displayName: String) : HasDisplayValue {
    FIRE("Г"),
    HOT("Т"),
    COLD("Х")
}