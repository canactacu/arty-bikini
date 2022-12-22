package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.file.OrderFileDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse
import ru.arty_bikini.crm.dto.work.WorkDTO


@Serializable
class AddWorkResponse(
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,

    val workDTO: WorkDTO? =null
): BaseResponse()