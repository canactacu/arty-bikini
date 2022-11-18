package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ для: получить список заказов по тренеру
@Serializable
class GetOrderByTrainerResponse(//статус
    val statusCode: String?,
    val orderList: List<OrderDTO>?
)