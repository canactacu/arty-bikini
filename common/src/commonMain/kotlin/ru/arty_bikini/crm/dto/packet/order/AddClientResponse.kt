package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ для: добавить лида-клиента
@Serializable
class AddClientResponse(//статус
    val statusCode: String, //ответ
    val orderr: OrderDTO? = null
)