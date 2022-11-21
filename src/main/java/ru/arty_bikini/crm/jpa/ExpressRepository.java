package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.ExpressEntity;

public interface ExpressRepository extends JpaRepository<ExpressEntity, Integer>  {
    public ExpressEntity getById(int id);
}
