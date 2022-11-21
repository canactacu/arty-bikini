package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.PageDTO
import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ архив
@Serializable
class GetArchiveResponse(//статус
    val statusCode: String, //список клиентов
    val orders: PageDTO<OrderDTO>?= null
)