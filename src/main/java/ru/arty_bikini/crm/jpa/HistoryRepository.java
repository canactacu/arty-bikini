package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.other.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    public HistoryEntity getById(int id);
}
