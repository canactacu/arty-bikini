package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.UserEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    public SessionEntity getByKey(String key);
}
