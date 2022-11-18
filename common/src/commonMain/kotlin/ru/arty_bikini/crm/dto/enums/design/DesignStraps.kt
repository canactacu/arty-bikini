package ru.arty_bikini.crm.dto.enums.design

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//верхние лямки дизайн
enum class DesignStraps (override val displayName: String) : HasDisplayValue {
    EMPTY("(без расклейки"),  //без расклейки, без коннекторов
    PASTED_UP("расклеено в цвет"),  //расклеено в цвет купальника(доплата)
    CONNECTOR_UP_DOWN("конекторы: Л+Т"),  //с коннекторами
    CONNECTOR_UP("конекторы:Лиф"),  //с коннекторами
    CONNECTOR_DOWN("конекторы:Трусы"),  //с коннекторами
    OTHER("другое(указать!)") //другое
}