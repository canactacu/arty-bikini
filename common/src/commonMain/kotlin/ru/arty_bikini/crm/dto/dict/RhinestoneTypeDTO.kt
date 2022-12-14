package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity
import ru.arty_bikini.crm.dto.enums.design.ManufacturerType
import ru.arty_bikini.crm.dto.enums.design.SizeTypeRhinston

@Serializable
class RhinestoneTypeDTO : IEntity {
    override var id = 0
    var manufacturer: String? = null // Производитель
    var sizeType: String? = null  // Тип (Пришивная и тд)
    var price = 0 // Цена за расклейку 1 шт"
}