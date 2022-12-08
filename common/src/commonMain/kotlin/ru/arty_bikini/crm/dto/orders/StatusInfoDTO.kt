package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.UserDTO

//класс статусов
@Serializable
class StatusInfoDTO {
    //Мерки
    var isMeasureAll = false //заполненость мерок
    var measureBy  : UserDTO? = null //кто заполнил дизайн
    var isMeasureAllTanya = false //Таня проверила дизайн
    
    //Дизайн
    var isDesignAll  = false //заполненость дизайна
    var designBy : UserDTO? = null //кто заполнил дизайн
    var isDesignAllTanya = false //Таня проверила
}