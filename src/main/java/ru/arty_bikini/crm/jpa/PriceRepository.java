package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.ExpressEntity;
import ru.arty_bikini.crm.data.dict.PriceEntity;

public interface PriceRepository extends JpaRepository<PriceEntity, Integer> {
    public PriceEntity getById(int id);
}
