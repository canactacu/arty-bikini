package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.IntervalEntity;
import ru.arty_bikini.crm.data.work.TourEntity;
import ru.arty_bikini.crm.data.work.WorkEntity;

import java.util.List;

public interface WorkRepository extends JpaRepository<WorkEntity, Integer> {
    public WorkEntity getById(int id); //найти работу, по id
    public List<WorkEntity> getByTour(TourEntity tour);//список работ по встрече

    //найти список работ по списку интервалов
    @Query(value = "SELECT * FROM works WHERE interval_id IN ( ?1 ) ", nativeQuery = true)
    public List<WorkEntity> getByIntervalList(List<IntervalEntity> intervals);
    
    public List<WorkEntity> getByOrder(OrderEntity order);
}
