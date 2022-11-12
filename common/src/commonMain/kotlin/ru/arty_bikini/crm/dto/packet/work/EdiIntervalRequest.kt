package ru.arty_bikini.crm.dto.packet.work

import java.time.LocalDateTime

//тело для: изменить интервал
class EdiIntervalRequest {
    var idInterval //уже существующий
            = 0
    var dateFinish //дата встречи новая
            : LocalDateTime? = null
}