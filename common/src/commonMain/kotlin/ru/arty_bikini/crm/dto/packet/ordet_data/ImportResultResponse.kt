package ru.arty_bikini.crm.dto.packet.ordet_data

import ru.arty_bikini.crm.dto.orders.google.DataGoogleDTO

//ответ для : получить результаты импорта
class ImportResultResponse(val statusCode: String, val dataGoogleDTOList: List<DataGoogleDTO>)