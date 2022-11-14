package ru.arty_bikini.crm.data.orders

//описывает типы данных получаемых от клиента
class OrderDataTypeDTO {
    var id = 0

    var name : String? = null //имя в системе

    var googleColumn : Int? = null //какая колонка

    var googleName : String? = null //имя колонки в гугл таблице
}