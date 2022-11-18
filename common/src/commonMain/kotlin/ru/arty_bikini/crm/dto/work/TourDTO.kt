package ru.arty_bikini.crm.dto.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity

@Serializable
class TourDTO : IEntity {
    override var id = 0
    var tour : Long? = null   //доп встреча
}