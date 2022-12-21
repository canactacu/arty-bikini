package ru.arty_bikini.crm.dto.packet.file

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.PageDTO
import ru.arty_bikini.crm.dto.file.FileDTO
import ru.arty_bikini.crm.dto.file.OrderFileDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

@Serializable
class GetFileResponse (
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,
    
    val filePage: PageDTO<FileDTO>?= null


): BaseResponse()