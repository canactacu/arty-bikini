package ru.arty_bikini.crm.dto

abstract class EntityDTO {
    
    abstract val id: Int
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EntityDTO) return false
        
        if (id != other.id) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        return id
    }
    
    
}
