package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.other.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {
    public NoteEntity getById(int id);
}
