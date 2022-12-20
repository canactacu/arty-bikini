package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arty_bikini.crm.data.file.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    public FileEntity getById(int id);
    
    @Query(value = "SELECT count(id) as count, sum(size) as totalSize FROM file", nativeQuery = true)
    public AllFileReport getAll();
    
   public class AllFileReport {
        private int count;
        private int totalSize;
        
        public int getCount() {
            return count;
        }
        
        public void setCount(int count) {
            this.count = count;
        }
        
        public int getTotalSize() {
            return totalSize;
        }
        
        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }
    }
}
