package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetEntity;

public interface CalcPresetRepository extends JpaRepository<CalcPresetEntity, Integer> {
    public CalcPresetEntity getById(int id);
}
