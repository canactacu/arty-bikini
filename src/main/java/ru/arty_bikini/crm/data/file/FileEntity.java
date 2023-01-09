package ru.arty_bikini.crm.data.file;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.utils.LocalDateTimeToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateTimeToLongSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "file")
@JsonFilter("entityFilter")
public class FileEntity {
    private int id;
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    @JsonDeserialize(using = LocalDateTimeToLongDeserializer.class)
    private LocalDateTime uploadAt;
    private UserEntity uploadBy;
    
    private String sha256;
    private String location;
    private long size;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
    @SequenceGenerator(name = "file_seq_gen", sequenceName = "file_id_seq")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "upload_at")
    public LocalDateTime getUploadAt() {
        return uploadAt;
    }
    
    public void setUploadAt(LocalDateTime uploadAt) {
        this.uploadAt = uploadAt;
    }
    
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "upload_by")
    public UserEntity getUploadBy() {
        return uploadBy;
    }
    
    public void setUploadBy(UserEntity uploadBy) {
        this.uploadBy = uploadBy;
    }
    
    @Column(name = "sha256")
    public String getSha256() {
        return sha256;
    }
    
    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }
    
    @Column
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    @Column(columnDefinition = "bigint NOT NULL DEFAULT 0")
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
}
