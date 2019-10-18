package net.kaaass.kmall.dao.entity;

import lombok.Data;
import net.kaaass.kmall.util.Constants;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GenericGenerator(name = Constants.ID_GENERATOR, strategy = Constants.UUID)
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private String id;

    @Column(name = "category_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent = null;

    @Column(name = "create_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createTime;
}
