package ru.arty_bikini.crm.dto.enums.personalData

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//статус клиента
enum class ClientType (override val displayName: String) : HasDisplayValue {
    LEAD("лид"),
    CLIENT("клиент"),
    ARCHIVE("архив")
}