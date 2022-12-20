package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.packet.BaseResponse
import ru.arty_bikini.crm.dto.work.WorkTypeDTO

@Serializable
class GetWorkTypeResponse  (
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,

    val WorkTypeList: List<WorkTypeDTO>?= null

): BaseResponse()