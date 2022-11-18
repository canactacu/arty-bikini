package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для:удалить работу со встречи
@Serializable
class DelWorkFromTourRequest (
    var idWork : Int = 0//id работы, которую хотим удалить со встречу
    )