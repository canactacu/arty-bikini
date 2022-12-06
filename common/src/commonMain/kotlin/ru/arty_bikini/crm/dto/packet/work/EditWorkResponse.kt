package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.WorkDTO

@Serializable
class EditWorkResponse(
    val statusCode: String,
    val workDTO: WorkDTO? = null
)