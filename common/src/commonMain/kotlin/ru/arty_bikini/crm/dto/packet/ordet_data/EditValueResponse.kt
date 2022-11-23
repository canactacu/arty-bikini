package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

@Serializable
class EditValueResponse(
    val errorCode: String,
    val orderDTO: OrderDTO? = null
    )