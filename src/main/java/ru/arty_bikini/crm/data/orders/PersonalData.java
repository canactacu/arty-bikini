package ru.arty_bikini.crm.data.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.enums.personalData.ClientLanguage;
import ru.arty_bikini.crm.utils.LocalDateToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateToLongSerializer;

import javax.persistence.*;
import java.time.LocalDate;


@Embeddable
public class PersonalData {
    private String name;//фамилия имя отчество

    private String prepayment;//предоплата
    private int payment;//цена
    private String telephon;//телефон
    private String city;//город
    private ClientLanguage language;
    private String addOrder;//дополнительно к заказу
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate createdTime;//дата появления лида
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate archiveTime;//дата попадания в архив
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate orderTime;//дата заказа(предоплата или заполнение мерок?)
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate competitionTime;//дата соревнований
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate neededTime;// дата когда нужен заказ
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate packageTime;//Дата, когда нужно отправить
    
    private int deliveryTime; //время доставки

    private String comment;//комментарий на чем остановились
    private TrainerEntity trainer;//тренер
    private boolean payTrainer;
    private int payMoneyTrainer;//сколько заплатили тренеру
    private int sale;
    
    @Column(name = "pd_sale", columnDefinition = "INT NOT NULL DEFAULT 0")
    public int getSale() {
        return sale;
    }
    
    public void setSale(int sale) {
        this.sale = sale;
    }
    
    @Column(name = "pd_pay_many_trainer", columnDefinition = "INT NOT NULL DEFAULT 0")
    public int getPayMoneyTrainer() {
        return payMoneyTrainer;
    }
    
    public void setPayMoneyTrainer(int payMoneyTrainer) {
        this.payMoneyTrainer = payMoneyTrainer;
    }
    
    @Column(name = "pd_pay_trainer", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isPayTrainer() {
        return payTrainer;
    }
    
    public void setPayTrainer(boolean payTrainer) {
        this.payTrainer = payTrainer;
    }
    
    @Column(name = "delivery_time", columnDefinition = "INT NOT NULL DEFAULT 0")
    public int getDeliveryTime() {
        return deliveryTime;
    }
    
    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    @Column(name = "package_time")
    public LocalDate getPackageTime() {
        return packageTime;
    }
    
    public void setPackageTime(LocalDate packageTime) {
        this.packageTime = packageTime;
    }
    
    @ManyToOne(targetEntity = TrainerEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "pd_trainer_id")
    public TrainerEntity getTrainer() {
        return trainer;
    }

    @Column(name = "pd_name")
    public String getName() {
        return name;
    }


    @Column(name = "pd_prepayment")
    public String getPrepayment() {
        return prepayment;
    }

    @Column(name = "pd_payment", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayment() {
        return payment;
    }

    @Column(name = "pd_telephon")
    public String getTelephon() {
        return telephon;
    }

    @Column(name = "pd_city")
    public String getCity() {
        return city;
    }

    @Column(name = "pd_language")
    @Enumerated(EnumType.STRING)
    public ClientLanguage getLanguage() {
        return language;
    }

    @Column(name = "pd_add_order")
    public String getAddOrder() {
        return addOrder;
    }

    @Column(name = "pd_order_time")
    public LocalDate getOrderTime() {
        return orderTime;
    }

    @Column(name = "pd_competition_time")
    public LocalDate getCompetitionTime() {
        return competitionTime;
    }

    @Column(name = "pd_needed_time")
    public LocalDate getNeededTime() {
        return neededTime;
    }

    @Column(name = "pd_comment")
    public String getComment() {
        return comment;
    }
    
    @Column(name = "pd_created_time")
    public LocalDate getCreatedTime() {
        return createdTime;
    }
    
    @Column(name = "pd_archive_time")
    public LocalDate getArchiveTime() {
        return archiveTime;
    }
    
    public void setArchiveTime(LocalDate archiveTime) {
        this.archiveTime = archiveTime;
    }
    
    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLanguage(ClientLanguage language) {
        this.language = language;
    }

    public void setAddOrder(String addOrder) {
        this.addOrder = addOrder;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public void setCompetitionTime(LocalDate competitionTime) {
        this.competitionTime = competitionTime;
    }

    public void setNeededTime(LocalDate neededTime) {
        this.neededTime = neededTime;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTrainer(TrainerEntity trainer) {
        this.trainer = trainer;
    }
}
