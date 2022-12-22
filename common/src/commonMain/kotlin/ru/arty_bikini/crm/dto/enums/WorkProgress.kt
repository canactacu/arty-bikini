package ru.arty_bikini.crm.dto.enums

enum class WorkProgress (override val displayName: String) : HasDisplayValue {
    STARTED("в процессе"),
    FINISHED("закончила")
}