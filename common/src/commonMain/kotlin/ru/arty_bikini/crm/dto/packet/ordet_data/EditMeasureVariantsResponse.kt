package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.orders.google.MeasureVariantsDTO

@Serializable
class EditMeasureVariantsResponse(
    val errorCode: String,
    val measureVariantsDTO: MeasureVariantsDTO? = null
    )