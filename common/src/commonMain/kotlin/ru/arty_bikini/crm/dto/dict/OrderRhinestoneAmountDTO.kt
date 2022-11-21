package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

@Serializable
class OrderRhinestoneAmountDTO {
    var id = 0
    var rhinestoneType: RhinestoneTypeDTO? = null
    var count = 0
    var order: OrderDTO? = null
}