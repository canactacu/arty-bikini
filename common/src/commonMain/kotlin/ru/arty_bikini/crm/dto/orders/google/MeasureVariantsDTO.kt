package ru.arty_bikini.crm.dto.orders.google

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO

@Serializable
class MeasureVariantsDTO {
    var id = 0
    var name: String? = null
    var orderDataType: OrderDataTypeDTO? = null
    var description: String? = null
    var googleName: String? = null
    var products: Set<ProductTypeDTO>? = null
}