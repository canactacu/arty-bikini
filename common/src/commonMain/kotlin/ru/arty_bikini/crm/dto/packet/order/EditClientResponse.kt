package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ для: изменить клиента
@Serializable
class EditClientResponse(//статус
    val statusCode: String?,
    val order: OrderDTO?
)