package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Integer> {
    public TrainerEntity getById(int id);//поиск TrainerEntity по id тренера
}
