package ru.arty_bikini.crm.dto.orders;

import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.data.enums.ClientLanguage;
import ru.arty_bikini.crm.dto.dict.TrainerDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

//класс данных секретных
public class PersonalDataDTO {
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

    private TrainerDTO trainer;//тренер

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public String getFamily() {
        return family;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPrepayment() {
        return prepayment;
    }

    public String getPayment() {
        return payment;
    }

    public String getTelephon() {
        return telephon;
    }

    public String getCity() {
        return city;
    }

    public ClientLanguage getLanguage() {
        return language;
    }

    public String getAddOrder() {
        return addOrder;
    }

    public LocalDateTime getOrderTime() {
        return OrderTime;
    }

    public LocalDateTime getCompetitionTime() {
        return CompetitionTime;
    }

    public LocalDateTime getNeededTime() {
        return neededTime;
    }

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

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }
}
