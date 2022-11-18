package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.google.OrderDataTypeDTO

@Serializable
class GetTypesResponse(
    val statusCode: String?,
    val types: List<OrderDataTypeDTO>?
)