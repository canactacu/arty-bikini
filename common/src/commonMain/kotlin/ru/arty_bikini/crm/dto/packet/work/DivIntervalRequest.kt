package ru.arty_bikini.crm.dto.packet.work

import java.time.LocalDateTime

//тело для:разделить интервал(добавить встречу)
class DivIntervalRequest {
    var idInterval //id интервала, в котором будем добавлять встречу
            = 0
    var tour //дата встречи
            : LocalDateTime? = null
}