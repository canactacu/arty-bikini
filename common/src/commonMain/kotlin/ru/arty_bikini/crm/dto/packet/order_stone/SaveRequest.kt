package ru.arty_bikini.crm.dto.packet.order_stone

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.stone.OrderRhinestoneAmountDTO

@Serializable
class SaveRequest (
    var idOrder: Int,
    var orderRhinestoneAmountDTOList: List<OrderRhinestoneAmountDTO>
)