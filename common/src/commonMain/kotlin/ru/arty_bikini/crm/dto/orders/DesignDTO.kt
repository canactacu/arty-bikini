package ru.arty_bikini.crm.dto.orders

import ru.arty_bikini.crm.dto.enums.DesignStraps
import ru.arty_bikini.crm.dto.enums.GlueDesign
import ru.arty_bikini.crm.dto.enums.TextileOrder
import ru.arty_bikini.crm.dto.UserDTO

//класс данных о дизайне
class DesignDTO {
    var color //цвет купальника
            : String? = null
    var textile //ткань купальника
            : TextileOrder? = null
    var amount //количество страз
            : String? = null
    var glue //клей
            : GlueDesign? = null
    var straps //верхние лямки дизайн
            : DesignStraps? = null
    var commentDesignUP //комментарий по дизайну лифа
            : String? = null
    var commentDesignDoun //комментарий по дизайну низ
            : String? = null
    var users //кто заполнил дизайн
            : UserDTO? = null
    var isUserTanya //таня проверила дизайн
            = false
}