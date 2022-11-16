package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;

import java.time.LocalDateTime;

public interface DataGoogleRepository extends JpaRepository<DataGoogleEntity, Integer> {
public DataGoogleEntity getByDataGoogle(LocalDateTime dataGoogle);
    //
}
