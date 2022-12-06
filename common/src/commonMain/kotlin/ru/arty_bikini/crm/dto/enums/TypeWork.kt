package ru.arty_bikini.crm.dto.enums

enum class TypeWork (override val displayName: String) : HasDisplayValue {
    // ALL,//лиф, трусы, лямки, сшито
    //    CUP_STRAP_CONECT,//лиф, лямки, сшито
    CUP("Чаш"),  //лиф
    HIP("Тру"),  //трусы

    //    HIP_STRAP,//трусы, лямки
    CONECT("Сшт"),  //сшито
    STRAP("Лям") //лямки
    //    CUP_STRAP,//лиф, лямки
    //    CUP_HIP//лиф, трусы
}