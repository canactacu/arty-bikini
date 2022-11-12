package ru.arty_bikini.crm.dto.packet.order

import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ для: изменить клиента
class EditClientResponse(//статус
    val statusCode: String?,
    val order: OrderDTO?
)