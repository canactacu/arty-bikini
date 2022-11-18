package ru.arty_bikini.crm.dto.packet.order

import kotlinx.serialization.Serializable

//тело для:получить список заказов по тренеру
@Serializable
class GetOrderByTrainerRequest (
    var idTrainer: Int = 0
    )