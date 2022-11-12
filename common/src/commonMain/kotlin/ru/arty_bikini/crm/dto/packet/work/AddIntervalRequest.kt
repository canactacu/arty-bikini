package ru.arty_bikini.crm.dto.packet.work

import java.time.LocalDateTime

//тело для: добавить интервал
class AddIntervalRequest {
    var datefinish //дата встречи
            : LocalDateTime? = null
}