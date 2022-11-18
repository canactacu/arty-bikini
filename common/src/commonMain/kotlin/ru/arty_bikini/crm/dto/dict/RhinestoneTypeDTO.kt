package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.design.ManufacturerType
import ru.arty_bikini.crm.dto.enums.design.SizeTypeRhinston

@Serializable
class RhinestoneTypeDTO {
    var id = 0
    var manufacturer: ManufacturerType? = null // Производитель
    var sizeTypeRhinston: SizeTypeRhinston? = null  // Тип (Пришивная и тд)
    var price = 0 // Цена за расклейку 1 шт"
}