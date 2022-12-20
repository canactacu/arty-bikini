package ru.arty_bikini.crm.dto.packet.file

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.file.OrderFileDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

@Serializable
class AddOrderFileResponse (
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,
    
    var orderFile : OrderFileDTO? = null
): BaseResponse()