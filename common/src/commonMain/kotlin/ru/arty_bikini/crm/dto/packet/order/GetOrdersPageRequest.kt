package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.filter.OrderFilter

@Serializable
class GetOrdersPageRequest(
    var filter: OrderFilter
)