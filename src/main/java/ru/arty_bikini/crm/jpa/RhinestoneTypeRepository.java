package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.RhinestoneTypeEntity;

public interface RhinestoneTypeRepository extends JpaRepository<RhinestoneTypeEntity, Integer> {
    public RhinestoneTypeEntity getById(int id);
}
