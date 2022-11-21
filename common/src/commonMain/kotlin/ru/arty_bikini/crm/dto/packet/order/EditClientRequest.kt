package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

//тело для: изменить клиента
@Serializable
class EditClientRequest (
    var order: OrderDTO
    )