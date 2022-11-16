package ru.arty_bikini.crm.dto.packet.work

//тело для:разделить интервал(добавить встречу)
class DivIntervalRequest {
    var idInterval //id интервала, в котором будем добавлять встречу
            = 0
    var tour //дата встречи
            : Long? = null
}