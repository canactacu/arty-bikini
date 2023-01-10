package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.PageDTO
import ru.arty_bikini.crm.dto.orders.OrderDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

@Serializable
class GetOrdersPageResponse (
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,

    val data: PageDTO<OrderDTO>?= null
): BaseResponse()