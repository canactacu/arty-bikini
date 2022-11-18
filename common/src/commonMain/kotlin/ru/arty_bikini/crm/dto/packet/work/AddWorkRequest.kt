package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.TypeWork

//тело для: добавить работу
@Serializable
class AddWorkRequest (
    var idOrder : Int = 0,   //ид заказа
    var idUserWork  : Int  = 0,   // ид сотрудника,
    var idInterval: Int = 0 ,   // ид интервала
    var typeWork  : List<TypeWork>? = null  // типы работ,которые будут сделаны
    )