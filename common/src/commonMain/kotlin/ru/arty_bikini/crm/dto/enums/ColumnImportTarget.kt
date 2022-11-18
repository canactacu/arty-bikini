package ru.arty_bikini.crm.dto.enums

enum class ColumnImportTarget(override val displayName: String) : HasDisplayValue {
    FIO("ФИО"),
    TELEPHONE("Телефон"),
    DATE("Дата")
}