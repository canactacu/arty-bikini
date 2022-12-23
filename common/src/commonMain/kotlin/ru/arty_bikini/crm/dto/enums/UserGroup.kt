package ru.arty_bikini.crm.dto.enums

import kotlin.jvm.JvmField

//права доступа
enum class UserGroup
    constructor(
    @JvmField val canViewHistory: Boolean,//таня и мы
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
        canViewHistory = true,
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
        canViewHistory = false,
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
        canViewHistory = false,
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
        canViewHistory = false,
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
        canViewHistory = false,
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