package ru.arty_bikini.crm.dto.packet.ordet_data

import ru.arty_bikini.crm.data.orders.OrderDataTypeDTO

class GetTypesResponse(
    val statusCode: String?,
    val types: List<OrderDataTypeDTO>?
)