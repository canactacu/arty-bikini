package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable

@Serializable
class PriceDTO {
    var id = 0
    var name: String? = null
    var count = 0
}