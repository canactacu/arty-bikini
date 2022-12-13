package ru.arty_bikini.crm.data.orders.stone;

import ru.arty_bikini.crm.dto.enums.CalcPresetRuleType;

public class CalcPresetRuleJson {
    private int stoneId;
    private boolean auto;
    private CalcPresetRuleType type;
    private int value;
    
    public int getStoneId() {
        return stoneId;
    }
    
    public void setStoneId(int stoneId) {
        this.stoneId = stoneId;
    }
    
    public boolean isAuto() {
        return auto;
    }
    
    public void setAuto(boolean auto) {
        this.auto = auto;
    }
    
    public CalcPresetRuleType getType() {
        return type;
    }
    
    public void setType(CalcPresetRuleType type) {
        this.type = type;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}


