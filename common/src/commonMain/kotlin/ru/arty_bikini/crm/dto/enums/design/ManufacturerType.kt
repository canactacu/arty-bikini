package ru.arty_bikini.crm.dto.enums.design

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

//производитель страз
enum class ManufacturerType (override val displayName: String) : HasDisplayValue {
    PRECISION("прециоза"),
    JAPAN("япония"),
    ACRYLIC("асфур")
}