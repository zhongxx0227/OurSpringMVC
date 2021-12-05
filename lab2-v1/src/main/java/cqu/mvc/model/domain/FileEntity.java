package cqu.mvc.model.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;

/**
 * 定义文件类
 */
@Entity
public class FileEntity {

    @Id
    @Column(length=50,nullable=false)
    private String id;

    @Column(length=50,nullable=false)
    private String name;

    public FileEntity(){
    }

    public FileEntity(String id,String name) {
        super();
        this.id=id;
        this.name=name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
