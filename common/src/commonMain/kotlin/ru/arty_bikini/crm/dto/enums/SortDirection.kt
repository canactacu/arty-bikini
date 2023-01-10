package ru.arty_bikini.crm.dto.enums


enum class SortDirection(override val displayName: String) : HasDisplayValue {
    ASC("А -> Я"),
    DESC("Я -> А")
}
