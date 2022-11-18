package ru.arty_bikini.crm.dto.enums.measure

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//своя, не своя
enum class BustNaturalMeasure(override val displayName: String) : HasDisplayValue {
    NATURAL("натуральная"),  //натуральная
    SILICON("сильконовая") //СИЛИКОНОВАЯ
}