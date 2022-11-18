package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ
@Serializable
class LinkOrderToImportResponse(
    val code: String,
    val orderDTO: OrderDTO
    )