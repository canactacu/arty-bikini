package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.WorkDTO

@Serializable
class EditWorkRequest (
    var workDTO: WorkDTO
    )