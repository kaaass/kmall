package net.kaaass.kmall.dao.entity;

import lombok.Data;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.vo.PromoteStyle;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "promote_strategy")
public class PromoteStrategyEntity {
    @Id
    @GenericGenerator(name = Constants.ID_GENERATOR, strategy = Constants.UUID)
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private String id;

    @Column(name = "promo_name")
    private String name;

    @Column(name = "promo_hint")
    private String hint;

    @Column(name = "clazz")
    private String clazz;

    @Column(name = "extra_param")
    private String param;

    @Column(name = "strategy_order")
    private int order;

    @Column(name = "style")
    @Enumerated(EnumType.STRING)
    private PromoteStyle style = PromoteStyle.INFO;

    @Column(name = "is_enabled")
    private boolean enabled = true;

    @Column(name = "last_update_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private Timestamp lastUpdateTime;
}
