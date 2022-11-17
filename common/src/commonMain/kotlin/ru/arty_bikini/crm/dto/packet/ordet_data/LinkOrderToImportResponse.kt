package ru.arty_bikini.crm.dto.packet.ordet_data

import ru.arty_bikini.crm.dto.orders.OrderDTO

//ответ
class LinkOrderToImportResponse(val code: String, val orderDTO: OrderDTO)