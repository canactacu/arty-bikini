package ru.arty_bikini.crm.dto.other

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.file.FileDTO

@Serializable
class NoteDTO : EntityDTO() {
    override var id = 0
    var path : String? = null
    var content : String? = null
    var file : FileDTO? = null

}