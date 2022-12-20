package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO
import ru.arty_bikini.crm.dto.enums.personalData.ClientLanguage

//класс данных секретных
@Serializable
class PersonalDataDTO {
    var name: String? = null //фамилия имя отчество
    var telephon: String? = null //телефон
    var city: String? = null  //город
    var language: ClientLanguage? = null
    
    var prepayment: String? = null  //предоплата
    var payment: Int = 0  //цена
    var trainer: TrainerDTO? = null  //тренер
    
    var orderTime: Long? = null //дата заказа(предоплата или заполнение мерок?)
    var competitionTime: Long? = null  //дата соревнований
    var neededTime: Long? = null  //дата, когда нужен заказ
    var packageTime : Long? = null //Дата, когда нужно отправить
    var deliveryTime : Int = 0 //время доставки
    
    var addOrder: String? = null //дополнительно к заказу
    var comment: String? = null //комментарий на чем остановились
    var createdTime : Long? = null //дата появления лида
    var archiveTime : Long? = null //дата попадания в архив
    var payTrainer = false
    var payMoney =0
    var sale = 0//скидка
}