package ru.arty_bikini.crm.dto.orders.stone

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO
import ru.arty_bikini.crm.dto.orders.OrderDTO

@Serializable
class OrderRhinestoneAmountDTO : EntityDTO() {
    override var id = 0
    var rhinestoneType: RhinestoneTypeDTO? = null
    var count = 0
    var order: OrderDTO? = null
}