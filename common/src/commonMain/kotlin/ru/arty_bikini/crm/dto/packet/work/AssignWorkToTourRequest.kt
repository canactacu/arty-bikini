package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для:добавить работу на отдельную встречу
@Serializable
class AssignWorkToTourRequest (
    var idWork : Int  //id работы, которую хотим добавить на встречу

)