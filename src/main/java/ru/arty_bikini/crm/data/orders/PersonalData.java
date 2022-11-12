package ru.arty_bikini.crm.data.orders;

import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.enums.ClientLanguage;

import javax.persistence.*;
import java.time.LocalDateTime;

//класс данных секретных
@Embeddable//помечает класс как встраиваемый в др сущность
public class PersonalData {
    private String family;//фамилия
    private String name;//имя
    private String patronymic;//отчество

    private String prepayment;//предоплата
    private String payment;//цена
    private String telephon;//телефон
    private String city;//город
    private ClientLanguage language;
    private String addOrder;//допольнительно к заказу

    private LocalDateTime OrderTime;//дата заказа(предоплата или заполнение мерок?)
    private LocalDateTime CompetitionTime;//дата соревнований
    private LocalDateTime neededTime;//дата, когда нужен заказ

    private String comment;//комментарий на чем остановились

    private TrainerEntity trainer;//тренер

    @ManyToOne(targetEntity = TrainerEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "pd_trainer_id")
    public TrainerEntity getTrainer() {
        return trainer;
    }

    @Column(name = "pd_family")
    public String getFamily() {
        return family;
    }

    @Column(name = "pd_name")
    public String getName() {
        return name;
    }

    @Column(name = "pd_patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    @Column(name = "pd_prepayment")
    public String getPrepayment() {
        return prepayment;
    }

    @Column(name = "pd_payment")
    public String getPayment() {
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
    public LocalDateTime getOrderTime() {
        return OrderTime;
    }

    @Column(name = "pd_competition_time")
    public LocalDateTime getCompetitionTime() {
        return CompetitionTime;
    }

    @Column(name = "pd_needed_time")
    public LocalDateTime getNeededTime() {
        return neededTime;
    }

    @Column(name = "pd_comment")
    public String getComment() {
        return comment;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment;
    }

    public void setPayment(String payment) {
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

    public void setOrderTime(LocalDateTime orderTime) {
        OrderTime = orderTime;
    }

    public void setCompetitionTime(LocalDateTime competitionTime) {
        CompetitionTime = competitionTime;
    }

    public void setNeededTime(LocalDateTime neededTime) {
        this.neededTime = neededTime;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTrainer(TrainerEntity trainer) {
        this.trainer = trainer;
    }
}
