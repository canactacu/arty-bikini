package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.work.IntervalEntity;
import ru.arty_bikini.crm.data.work.TourEntity;

public interface TourRepository extends JpaRepository<TourEntity, Integer> {
    public TourEntity getByTour(IntervalEntity interval);//поиск встречи по интервалу
}
