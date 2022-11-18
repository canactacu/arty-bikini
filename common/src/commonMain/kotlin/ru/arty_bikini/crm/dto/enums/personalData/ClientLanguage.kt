package ru.arty_bikini.crm.dto.enums.personalData

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//язык клиента
//наследует от интерфейса HasDisplayValue
// ()=создан конструктор, override= переопределяет функцию.(т.е берем именно эту функцию, а не ту)
enum class ClientLanguage(override val displayName: String) : HasDisplayValue {
    RUSSIAN("Русский"),
    ENGLISH("Английский"),
    OTHER("Другой")
}