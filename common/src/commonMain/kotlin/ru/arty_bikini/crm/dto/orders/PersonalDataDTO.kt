package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO
import ru.arty_bikini.crm.dto.enums.personalData.ClientLanguage

//класс данных секретных
@Serializable
class PersonalDataDTO {
    var name: String? = null //фамилия имя отчество
    var prepayment: String? = null  //предоплата
    var payment: Int? = null  //цена
    var telephon: String? = null //телефон
    var city: String? = null  //город
    var language: ClientLanguage? = null
    var addOrder: String? = null //допольнительно к заказу
    var orderTime: Long? = null //дата заказа(предоплата или заполнение мерок?)
    var competitionTime: Long? = null  //дата соревнований
    var neededTime: Long? = null  //дата, когда нужен заказ
    var comment: String? = null //комментарий на чем остановились
    var trainer: TrainerDTO? = null  //тренер
}