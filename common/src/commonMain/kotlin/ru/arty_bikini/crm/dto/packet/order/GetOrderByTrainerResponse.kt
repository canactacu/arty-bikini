package ru.arty_bikini.crm.dto.packet.order

import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ для: получить список заказов по тренеру
class GetOrderByTrainerResponse(//статус
    val statusCode: String?,
    val orderList: List<OrderDTO>?
)