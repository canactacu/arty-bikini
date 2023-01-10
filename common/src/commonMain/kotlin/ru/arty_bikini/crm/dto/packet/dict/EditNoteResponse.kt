package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.other.NoteDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

@Serializable
class EditNoteResponse (
    override val ok: Boolean,
    override val statusCode: String,
    override val displayMessage: String?,

    var note : NoteDTO? = null
): BaseResponse()