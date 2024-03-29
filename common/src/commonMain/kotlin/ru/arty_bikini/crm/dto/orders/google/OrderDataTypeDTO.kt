package ru.arty_bikini.crm.dto.orders.google

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO
import ru.arty_bikini.crm.dto.enums.ColumnImportTarget
import ru.arty_bikini.crm.dto.enums.measure.DataDisplayCategory

//описывает типы данных получаемых от клиента
@Serializable
class OrderDataTypeDTO : EntityDTO() {
    override var id = 0
    var name : String? = null //имя в системе
    
    var googleColumn : Int? = null //какая колонка
    var googleName : String? = null //имя колонки в гугл таблице
    
    var target : ColumnImportTarget? = null
    
    var displayCategory : DataDisplayCategory? = null
    var displayPosition : String? = null
    
    var products : Set<ProductTypeDTO>? = null
    var comment : String? = null
}