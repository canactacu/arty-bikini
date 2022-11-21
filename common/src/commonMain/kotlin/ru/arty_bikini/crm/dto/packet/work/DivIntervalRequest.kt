package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для:разделить интервал(добавить встречу)
@Serializable
class DivIntervalRequest (
    var idInterval : Int , //id интервала, в котором будем добавлять встречу
    var tour : Long  //дата встречи
    
    )