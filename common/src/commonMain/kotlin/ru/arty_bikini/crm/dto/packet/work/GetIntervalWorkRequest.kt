package ru.arty_bikini.crm.dto.packet.work

import java.time.LocalDateTime

//тело для:получить список работ...
class GetIntervalWorkRequest {
    var dateTime //получаем дату начала
            : LocalDateTime? = null
}