package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.UserDTO
import ru.arty_bikini.crm.dto.enums.design.DesignStraps
import ru.arty_bikini.crm.dto.enums.design.GlueDesign
import ru.arty_bikini.crm.dto.enums.design.TextileOrder

//класс данных о дизайне
@Serializable
class DesignDTO {
    var color: String? = null  //цвет купальника
    var textile: TextileOrder? = null  //ткань купальника
    var amount: String? = null  //количество страз
    var glue: GlueDesign? = null //клей
    var straps: DesignStraps? = null //верхние лямки дизайн
    var commentDesignUP: String? = null  //комментарий по дизайну лифа
    var commentDesignDoun: String? = null  //комментарий по дизайну низ
    var users: UserDTO? = null  //кто заполнил дизайн
    var isUserTanya = false  //таня проверила дизайн
}