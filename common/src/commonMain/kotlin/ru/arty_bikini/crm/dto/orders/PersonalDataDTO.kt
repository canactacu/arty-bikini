package ru.arty_bikini.crm.dto.orders

import ru.arty_bikini.crm.dto.dict.TrainerDTO
import ru.arty_bikini.crm.dto.enums.ClientLanguage

//класс данных секретных
class PersonalDataDTO {
    var name //фамилия имя отчество
            : String? = null
    var prepayment //предоплата
            : String? = null
    var payment //цена
            : String? = null
    var telephon //телефон
            : String? = null
    var city //город
            : String? = null
    var language: ClientLanguage? = null
    var addOrder //допольнительно к заказу
            : String? = null
    var orderTime //дата заказа(предоплата или заполнение мерок?)
            : Long? = null
    var competitionTime //дата соревнований
            : Long? = null
    var neededTime //дата, когда нужен заказ
            : Long? = null
    var comment //комментарий на чем остановились
            : String? = null
    var trainer //тренер
            : TrainerDTO? = null
}