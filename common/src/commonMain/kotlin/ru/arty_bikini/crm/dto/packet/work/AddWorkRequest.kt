package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.enums.TypeWork

//тело для: добавить работу
class AddWorkRequest {
    var idOrder //ид заказа,
            = 0
    var idUserWork // ид сотрудника,
            = 0
    var idInterval // ид интервала,
            = 0
    var typeWork // типы работ,которые будут сделаны
            : List<TypeWork>? = null
}