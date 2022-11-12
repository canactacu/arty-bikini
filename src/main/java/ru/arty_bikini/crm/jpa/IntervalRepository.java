package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.IntervalEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IntervalRepository extends JpaRepository<IntervalEntity, Integer> {
    public IntervalEntity getById(int id);//найти интервал по заданному id

    public IntervalEntity getByDateFinish(LocalDateTime dateFinish); //поиск интервала по финешной дате

    @Query("SELECT * FROM intervals ORDER BY data_finish DESC LIMIT 1")
    public IntervalEntity getLast();//ищет интервал последней даты

    @Query("SELECT * FROM intervals ORDER BY data_finish ASC LIMIT 1")
    public IntervalEntity getFirst();//ищет интервал первой даты

    //                      0-5 | 5      -       15  | 15-20 | 20    -      40 | 40-50
    //                               start = 10                     end = 30
    //ищет список интервалов от start до end включая частичные попадания
    @Query("SELECT * FROM intervals WHERE ?1 < data_finish AND ?2 > data_start")
    public List<IntervalEntity> getIntervalFromStartToEnd(LocalDateTime start, LocalDateTime end);

    public IntervalEntity getByStart(LocalDateTime dateFinish);//ищи интервал по стартовой дате в колонке старт



    //@Query("SELECT * FROM intervals ORDER BY data_finish DESC LIMIT 1")
    //public IntervalEntity get;//дата, перед указанной датой

}
