package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.orders.google.MeasureVariantsEntity;
import ru.arty_bikini.crm.data.work.IntervalEntity;

public interface MeasureVariantsRepository extends JpaRepository<MeasureVariantsEntity, Integer> {
    public MeasureVariantsEntity getById(int id);
}
