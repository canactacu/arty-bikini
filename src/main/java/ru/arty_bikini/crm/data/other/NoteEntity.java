package ru.arty_bikini.crm.data.other;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.data.file.FileEntity;

import javax.persistence.*;

@Entity(name = "note")
@JsonFilter("entityFilter")
public class NoteEntity {
    private int id;
    private String path;
    private String content;
    private FileEntity file;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_seq_gen")
    @SequenceGenerator(name = "note_seq_gen", sequenceName = "note_id_seq")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(targetEntity = FileEntity.class)
    @JoinColumn(name = "file_id")
    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}
