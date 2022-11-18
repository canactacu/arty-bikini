package ru.arty_bikini.crm.dto.enums.design

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//ткань изделия
enum class TextileOrder (override val displayName: String) : HasDisplayValue {
    SUPPLEX("Бифлекс"),  //бифлекс
    VELVET("Бархат"),  //бархат
    GOLOGRAMMA("Голограмма(с доплатой)"),
    NON_STANDART("нестандартная (с доплатой)"),
    OTHER("Другая(без доплаты)") //другое
}