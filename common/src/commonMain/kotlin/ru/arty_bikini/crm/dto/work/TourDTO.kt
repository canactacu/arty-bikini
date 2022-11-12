package ru.arty_bikini.crm.dto.work

import kotlinx.datetime.LocalDateTime
import ru.arty_bikini.crm.dto.IEntity

class TourDTO : IEntity {
    override var id = 0
    var tour //доп встреча
            : LocalDateTime? = null
}