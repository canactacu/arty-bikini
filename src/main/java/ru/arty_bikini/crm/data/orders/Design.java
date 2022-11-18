package ru.arty_bikini.crm.data.orders;

import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.dto.enums.design.DesignStraps;
import ru.arty_bikini.crm.dto.enums.design.GlueDesign;
import ru.arty_bikini.crm.dto.enums.design.TextileOrder;

import javax.persistence.*;

//класс данных о дизайне
@Embeddable//помечает класс как встраиваемый в др сущность
public class Design {
    private String color;//цвет купальника
    private TextileOrder textile;//ткань купальника

    private String amount;//количество страз
    private GlueDesign glue;//клей
    private DesignStraps straps;//верхние лямки дизайн
    private String commentDesignUP;//комментарий по дизайну лифа
    private String commentDesignDoun;//комментарий по дизайну низ

    private UserEntity designer;//кто заполнил дизайн
    private boolean userTanya;//таня проверила дизайн

    @Column(name = "d_color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "d_textile")
    @Enumerated(EnumType.STRING)
    public TextileOrder getTextile() {
        return textile;
    }

    public void setTextile(TextileOrder textile) {
        this.textile = textile;
    }

    @Column(name = "d_amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Column(name = "d_glue")
    @Enumerated(EnumType.STRING)
    public GlueDesign getGlue() {
        return glue;
    }

    public void setGlue(GlueDesign glue) {
        this.glue = glue;
    }

    @Column(name = "d_straps")
    @Enumerated(EnumType.STRING)
    public DesignStraps getStraps() {
        return straps;
    }

    public void setStraps(DesignStraps straps) {
        this.straps = straps;
    }

    @Column(name = "d_comment_design_up")
    public String getCommentDesignUP() {
        return commentDesignUP;
    }

    public void setCommentDesignUP(String commentDesignUP) {
        this.commentDesignUP = commentDesignUP;
    }

    @Column(name = "comment_design_doun")
    public String getCommentDesignDoun() {
        return commentDesignDoun;
    }

    public void setCommentDesignDoun(String commentDesignDoun) {
        this.commentDesignDoun = commentDesignDoun;
    }

    @ManyToOne(targetEntity = UserEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "d_user_id")//даем название колонке
    public UserEntity getDesigner() {
        return designer;
    }

    public void setDesigner(UserEntity designer) {
        this.designer = designer;
    }

    @Column(name = "d_user_tanya")
    public boolean isUserTanya() {
        return userTanya;
    }

    public void setUserTanya(boolean userTanya) {
        this.userTanya = userTanya;
    }
}

