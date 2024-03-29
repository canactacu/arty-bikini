package ru.arty_bikini.crm.data.orders.google;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.arty_bikini.crm.dto.enums.ColumnImportTarget;
import ru.arty_bikini.crm.dto.enums.measure.DataDisplayCategory;

import javax.persistence.*;

//описывает типы данных получаемых от клиента   (столбики)
@Entity(name = "orders_data_type")
@JsonFilter("entityFilter")
public class OrderDataTypeEntity {
    private int id;
    private String name;//имя в системе
    
    private int googleColumn;//какая колонка
    private String googleName;//имя колонки в гугл таблице
    
    private ColumnImportTarget target;
    
    private DataDisplayCategory displayCategory;
    private String displayPosition;
    
    private String productJson;
    private String comment;
    
    @Column(columnDefinition = "TEXT")
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_data_type_seq_gen")
    @SequenceGenerator(name = "orders_data_type_seq_gen", sequenceName = "orders_data_type_id_seq")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    @Column(name = "product_json")
    public String getProductJson() {
        return productJson;
    }
    
    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }
    
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column
    @Enumerated(EnumType.STRING)
    public ColumnImportTarget getTarget() {
        return target;
    }

    public void setTarget(ColumnImportTarget target) {
        this.target = target;
    }

    @Column(name = "google_column")
    public int getGoogleColumn() {
        return googleColumn;
    }

    public void setGoogleColumn(int googleColumn) {
        this.googleColumn = googleColumn;
    }

    @Column(name = "google_name")
    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }
    
    @Column(name = "data_display_category")
    public DataDisplayCategory getDisplayCategory() {
        return displayCategory;
    }
    
    public void setDisplayCategory(DataDisplayCategory displayCategory) {
        this.displayCategory = displayCategory;
    }
    
    @Column(name = "display_position")
    public String getDisplayPosition() {
        return displayPosition;
    }
    
    public void setDisplayPosition(String displayPosition) {
        this.displayPosition = displayPosition;
    }
}
