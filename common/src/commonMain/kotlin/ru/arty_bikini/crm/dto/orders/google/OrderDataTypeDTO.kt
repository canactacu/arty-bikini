package ru.arty_bikini.crm.dto.orders.google

import ru.arty_bikini.crm.dto.enums.ColumnImportTarget

//описывает типы данных получаемых от клиента
class OrderDataTypeDTO {
    var id = 0

    var name : String? = null //имя в системе

    var target : ColumnImportTarget? = null

    var googleColumn : Int? = null //какая колонка

    var googleName : String? = null //имя колонки в гугл таблице
}