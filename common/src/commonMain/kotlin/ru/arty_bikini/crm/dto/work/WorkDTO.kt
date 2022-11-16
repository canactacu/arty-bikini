package ru.arty_bikini.crm.dto.work

import ru.arty_bikini.crm.dto.IEntity
import ru.arty_bikini.crm.dto.UserDTO
import ru.arty_bikini.crm.dto.enums.TypeWork
import ru.arty_bikini.crm.dto.orders.OrderDTO

//класс описывает работы над заказами order
class WorkDTO : IEntity {
    override var id = 0
    var order //заказ
            : OrderDTO? = null
    var works //части чаказа
            : Set<TypeWork>? = null
    var user //работник
            : UserDTO? = null
    var interval //интервал
            : IntervalDTO? = null
    var tour //доп встреча
            : TourDTO? = null

}