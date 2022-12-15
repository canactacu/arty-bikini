package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.OrderDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

@Serializable
class GetOrderResponse (
    override val statusCode : String,
    override val ok : Boolean,
    override val displayMessage : String?,

    val orderDTO: OrderDTO?= null

) : BaseResponse()