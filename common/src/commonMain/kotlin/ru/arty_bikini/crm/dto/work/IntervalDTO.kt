package ru.arty_bikini.crm.dto.work

import ru.arty_bikini.crm.dto.IEntity

class IntervalDTO: IEntity {
    override var id = 0
    var dateStart: Long? = null
    var dateFinish: Long? = null
    var tour //доп встреча
            : TourDTO? = null
}