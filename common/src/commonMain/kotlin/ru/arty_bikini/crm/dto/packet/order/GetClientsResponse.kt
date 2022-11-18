package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ список лидо и клиентов
@Serializable
class GetClientsResponse(//статус
    val statusCode: String?, //список клиентов
    val orders: List<OrderDTO>?
)