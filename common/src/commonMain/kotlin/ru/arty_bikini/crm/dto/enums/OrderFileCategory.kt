package ru.arty_bikini.crm.dto.enums

enum class OrderFileCategory (override val displayName: String) : HasDisplayValue{
    TEXTILE("Ткань"),
    DESIGN("Дизайн"),
    MEASURES("Мерки"),
    OTHER("Прочее")
}