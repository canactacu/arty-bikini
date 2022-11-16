package ru.arty_bikini.crm.dto.packet.work

//тело для: изменить интервал
class EdiIntervalRequest {
    var idInterval //уже существующий
            = 0
    var dateFinish //дата встречи новая
            : Long? = null
}