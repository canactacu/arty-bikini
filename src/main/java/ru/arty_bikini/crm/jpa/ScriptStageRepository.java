package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.ScriptStageEntity;

public interface ScriptStageRepository extends JpaRepository<ScriptStageEntity, Integer> {
    public ScriptStageEntity getById(int id);
}
