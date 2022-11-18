package ru.arty_bikini.crm.dto.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity
import ru.arty_bikini.crm.dto.UserDTO
import ru.arty_bikini.crm.dto.enums.TypeWork
import ru.arty_bikini.crm.dto.orders.OrderDTO

//класс описывает работы над заказами order
@Serializable
class WorkDTO : IEntity {
    override var id = 0
    var order : OrderDTO? = null //заказ
    var works : Set<TypeWork>? = null //части чаказа
    var user : UserDTO? = null  //работник
    var interval : IntervalDTO? = null
    var tour: TourDTO? = null  //доп встреча

}