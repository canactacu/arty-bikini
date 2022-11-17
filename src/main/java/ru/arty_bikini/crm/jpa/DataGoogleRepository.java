package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface DataGoogleRepository extends JpaRepository<DataGoogleEntity, Integer> {
    public DataGoogleEntity getByDateGoogle(LocalDateTime dateGoogle);

    public List<DataGoogleEntity> getByConnected(boolean connected);//список всех по параметру connected
    public DataGoogleEntity getById(int id);
    //
}
