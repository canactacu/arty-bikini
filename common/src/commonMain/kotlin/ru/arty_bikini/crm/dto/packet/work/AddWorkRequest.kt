package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.TypeWork
import ru.arty_bikini.crm.dto.work.WorkDTO
import ru.arty_bikini.crm.dto.work.WorkTypeDTO

//тело для: добавить работу
@Serializable
class AddWorkRequest (
    var workDTO: WorkDTO
    )
