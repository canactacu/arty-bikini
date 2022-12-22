package ru.arty_bikini.crm.dto.enums

import kotlin.jvm.JvmField

//права доступа
enum class UserGroup
    constructor(
    @JvmField val canEditUsers: Boolean,//таня и мы
    @JvmField val canViewUsers: Boolean,//все
    //тренера, продукты(слитный, фитнес бикини),
    // тип страз, срочность, лямки, допы
    @JvmField val canEditDict: Boolean,
    @JvmField val canViewDict: Boolean,
    //заказ
    @JvmField val canEditOrder: Boolean,
    @JvmField val canViewOrder: Boolean,
    //получить список заказов по тренеру
    @JvmField val canViewTrainer: Boolean,
    //работы и интервалы
    @JvmField val canEditWork: Boolean,
    @JvmField val canViewWork: Boolean,
    //гугол столбики
    @JvmField val canEditColumn: Boolean,
    @JvmField val canViewColumn: Boolean,
    
    override val displayName: String,
) : HasDisplayValue {
    
    ADMIN(
        canEditUsers = true,
        canViewUsers = true,
        canEditDict = true,
        canViewDict = true,
        canEditOrder = true,
        canViewOrder = true,
        canViewTrainer = true,
        canEditWork = true,
        canViewWork = true,
        canEditColumn = true,
        canViewColumn = true,
        displayName = "Админ"
    ),
    TANYA(
        canEditUsers = true,
        canViewUsers = true,
        
        canEditDict = true,
        canViewDict = true,
        
        canEditOrder = true,
        canViewOrder = true,
        
        canViewTrainer = true,
        
        canEditWork = true,
        canViewWork = true,
        
        canEditColumn = false,
        canViewColumn = true,
        displayName = "Татьяна"
        ),
    MANAGER(
        canEditUsers = false,
        canViewUsers = true,
    
        canEditDict = false,
        canViewDict = true,
    
        canEditOrder = true,
        canViewOrder = true,
    
        canViewTrainer = false,
    
        canEditWork = false,
        canViewWork = true,
    
        canEditColumn = false,
        canViewColumn = true,
        displayName = "Менеджер"
    ),
    SEAMSTRESS(
        canEditUsers = false,
        canViewUsers = true,
    
        canEditDict = false,
        canViewDict = true,
    
        canEditOrder = false,
        canViewOrder = true,
    
        canViewTrainer = false,
    
        canEditWork = false,
        canViewWork = true,
    
        canEditColumn = false,
        canViewColumn = true,
        displayName = "Швея"
    ),
    GLUER(
        canEditUsers = false,
        canViewUsers = true,
    
        canEditDict = false,
        canViewDict = true,
    
        canEditOrder = false,
        canViewOrder = true,
    
        canViewTrainer = false,
    
        canEditWork = false,
        canViewWork = true,
    
        canEditColumn = false,
        canViewColumn = true,
        displayName = "Расклейщица"
    ),
    ;
    
    
}