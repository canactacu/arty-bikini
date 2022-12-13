package ru.arty_bikini.crm.dto.packet.order_stone

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetDTO

@Serializable
class AddPresetsResponse(
    val status: String,
    val calcPresetDTO: CalcPresetDTO? = null
    )