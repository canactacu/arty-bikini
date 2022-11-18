package ru.arty_bikini.crm.dto.enums

enum class TypeWork (override val displayName: String) : HasDisplayValue {
    // ALL,//лиф, трусы, лямки, сшито
    //    CUP_STRAP_CONECT,//лиф, лямки, сшито
    CUP("Лиф"),  //лиф
    HIP("Трусы"),  //трусы

    //    HIP_STRAP,//трусы, лямки
    CONECT("Сшить"),  //сшито
    STRAP("Лямки") //лямки
    //    CUP_STRAP,//лиф, лямки
    //    CUP_HIP//лиф, трусы
}