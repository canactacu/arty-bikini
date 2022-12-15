package ru.arty_bikini.crm.dto.orders.google

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO

@Serializable
class MeasureVariantsDTO : EntityDTO() {
    override var id = 0
    var name: String? = null
    var orderDataType: OrderDataTypeDTO? = null
    var description: String? = null
    var googleName: String? = null
    var priority : Int = 0
    var products: Set<ProductTypeDTO>? = null
}