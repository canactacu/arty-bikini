package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

@Serializable
class GetIntervalWorkRequest (
    var dateTime  : Long //получаем дату начала
    
    )