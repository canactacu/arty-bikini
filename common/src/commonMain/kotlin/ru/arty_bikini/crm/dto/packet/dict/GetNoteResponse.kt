package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.file.OrderFileDTO
import ru.arty_bikini.crm.dto.other.NoteDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

@Serializable
class GetNoteResponse (
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,

    var noteList : List<NoteDTO>? = null
): BaseResponse()