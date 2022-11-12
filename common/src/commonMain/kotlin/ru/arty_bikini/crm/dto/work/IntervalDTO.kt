package ru.arty_bikini.crm.dto.work

import kotlinx.datetime.LocalDateTime
import ru.arty_bikini.crm.dto.IEntity

class IntervalDTO: IEntity {
    override var id = 0
    var dateStart: LocalDateTime? = null
    var dateFinish: LocalDateTime? = null
    var tour //доп встреча
            : TourDTO? = null
}