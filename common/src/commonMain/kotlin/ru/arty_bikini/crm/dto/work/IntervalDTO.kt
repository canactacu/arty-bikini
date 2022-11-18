package ru.arty_bikini.crm.dto.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity

@Serializable
class IntervalDTO: IEntity {
    override var id = 0
    var dateStart: Long? = null
    var dateFinish: Long? = null
    var tour: TourDTO? = null //доп встреча
}