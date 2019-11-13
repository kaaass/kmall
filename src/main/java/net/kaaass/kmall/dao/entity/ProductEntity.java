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
@Table(name = "product")
public class ProductEntity {
    @Id
    @GenericGenerator(name = Constants.ID_GENERATOR, strategy = Constants.UUID)
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private String id;

    @Column(name = "product_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "thumbnail",
            unique = true)
    private MediaEntity thumbnail;

    @Column(name = "price")
    private float price;

    @Column(name = "mail_price")
    private float mailPrice;

    @Column(name = "buy_limit",
            columnDefinition = "INT DEFAULT -1")
    private int buyLimit = -1;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "index_order",
            columnDefinition = "INT DEFAULT -1")
    private int indexOrder = -1;

    // TODO 添加belongTo字段，以实现商品选择不同颜色种类

    @OneToOne(targetEntity = ProductStorageEntity.class,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "storage_id")
    private ProductStorageEntity storage;

    @Column(name = "start_sell_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp startSellTime;

    @Column(name = "create_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createTime;

    @Column(name = "last_update_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private Timestamp lastUpdateTime;
}
