package ru.arty_bikini.crm.dto.enums.design

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//размер страз
enum class SizeTypeRhinston (override val displayName: String) : HasDisplayValue {
    SEW_BIG("Пришивные Большие"),//пришивные большие стразы
    SEW_AVERAGE("Пришивные Средние"),//пришивная стразы средние
    STANDART16("стандарт ss16"),
    STANDART20("стандарт ss20")
}