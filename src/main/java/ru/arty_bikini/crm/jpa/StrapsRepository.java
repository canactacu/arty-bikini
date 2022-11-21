package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.StrapsEntity;

public interface StrapsRepository extends JpaRepository<StrapsEntity, Integer> {
}
