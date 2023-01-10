package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.other.NoteDTO

@Serializable
class AddNoteRequest(
    var note : NoteDTO
)