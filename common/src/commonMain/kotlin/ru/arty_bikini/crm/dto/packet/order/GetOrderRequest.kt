package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable

@Serializable
class GetOrderRequest (
    var idOrder : Int
    )