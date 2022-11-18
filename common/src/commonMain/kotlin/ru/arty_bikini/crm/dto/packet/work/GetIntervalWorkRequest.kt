package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для:получить список работ...
@Serializable
class GetIntervalWorkRequest (
    var dateTime  : Long? = null //получаем дату начала
    
    )