package ru.arty_bikini.crm.dto.filter

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.SortDirection

@Serializable
open class EntityFilter {
    var page: Int = 0

    var orderColumn: String? = null//столбик
    var orderDirection: SortDirection = SortDirection.ASC

}