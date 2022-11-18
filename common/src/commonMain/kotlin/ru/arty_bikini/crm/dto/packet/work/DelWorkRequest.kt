package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для:удалить работу
@Serializable
class DelWorkRequest (
    var idWorc : Int = 0 //id удаляемой работы
    )