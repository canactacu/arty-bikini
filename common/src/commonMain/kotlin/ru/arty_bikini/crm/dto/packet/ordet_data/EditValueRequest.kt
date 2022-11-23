package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable

@Serializable
class EditValueRequest (
    var orderId : Int,
    var orderDataTypeId : Int ,
    var value: String
    )