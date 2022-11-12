package ru.arty_bikini.crm.dto.packet.order

import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ для: добавить лида-клиента
class AddClientResponse(//статус
    val statusCode: String?, //ответ
    val orderr: OrderDTO?
)