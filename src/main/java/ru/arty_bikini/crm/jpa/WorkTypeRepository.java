package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.work.WorkTypeEntity;

public interface WorkTypeRepository extends JpaRepository<WorkTypeEntity, Integer> {
    public WorkTypeEntity getById(int id);
}
