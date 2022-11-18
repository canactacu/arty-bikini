package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для: изменить интервал
@Serializable
class EditIntervalRequest (
    
    var idInterval : Int = 0,  //уже существующий
    var dateFinish  : Long? = null //дата встречи новая
    
    )