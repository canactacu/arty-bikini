package ru.arty_bikini.crm.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arty_bikini.crm.data.file.FileEntity;
import ru.arty_bikini.crm.data.file.OrderFileEntity;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    public FileEntity getById(int id);
    
    @Query(value = "SELECT count(id) as count, sum(size) as totalSize FROM file", nativeQuery = true)
    public AllFileReport getAll();
    
    public interface AllFileReport {
        public int getCount();
        
        public int getTotalSize();
    }
}
