package ru.arty_bikini.crm.dto.enums.design

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//клей
enum class GlueDesign(override val displayName: String) : HasDisplayValue {
    STANDART("стандарт"),
    E6000("Е6000(с доплатой)"),
    OTHER("другой(указать!)")
}