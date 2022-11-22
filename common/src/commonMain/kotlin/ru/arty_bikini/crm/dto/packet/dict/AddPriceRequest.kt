package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.PriceDTO

@Serializable
class AddPriceRequest (
    var priceDTO: PriceDTO
    )