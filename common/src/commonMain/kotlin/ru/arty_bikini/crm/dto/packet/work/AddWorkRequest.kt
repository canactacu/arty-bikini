package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.TypeWork

//тело для: добавить работу
@Serializable
class AddWorkRequest (
    var idOrder : Int ,   //ид заказа
    var idUserWork  : Int  ,   // ид сотрудника,
    var idInterval: Int  ,   // ид интервала
    var typeWork  : List<TypeWork>  // типы работ,которые будут сделаны
    )