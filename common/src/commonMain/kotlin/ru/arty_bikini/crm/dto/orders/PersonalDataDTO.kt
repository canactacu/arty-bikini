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
    var addOrder: String? = null //дополнительно к заказу
    var comment: String? = null //комментарий на чем остановились
    
    var packageNow = false
    
    var createdTime : Long? = null //дата появления лида
    var archiveTime : Long? = null //дата попадания в архив
    var orderTime: Long? = null //дата заказа(предоплата или заполнение мерок?)
    var packageTime : Long? = null //Дата, когда нужно отправить
    
    var packageManager : Long? = null ///дата, когда нужно отправить, которую указал менеджер(менеджер дата отправки)
    var packageManufacture : Long? = null //дата, которую посчитала система (4 недели пошив от даты заполнения мерок)
    var packageClient : Long? = null //дата, которую посчитала система, (клиент указал дату нужности или соревнований + время на пошив)
    
    var deliveryTime : Int = 0 //время доставки
    var userPackage: String? = null
    
    var trainer: TrainerDTO? = null  //тренер
    var payTrainer = false
    var payMoney =0
    var sale = 0//скидка
    
    
}