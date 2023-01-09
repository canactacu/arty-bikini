package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable

@Serializable
class HideRequest (
    var orderDataId : Int,
    var exclude : Boolean
    )