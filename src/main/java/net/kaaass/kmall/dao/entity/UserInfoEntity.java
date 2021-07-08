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
@Table(name = "user_info")
public class UserInfoEntity {
    @Id
    @GenericGenerator(name = Constants.ID_GENERATOR, strategy = Constants.UUID)
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private String id;

    @OneToOne
    @JoinColumn(name = "uid",
            unique = true)
    private UserAuthEntity auth;

    @Column(name = "wechat",
            columnDefinition = "TEXT DEFAULT NULL")
    private String wechat;

    @ManyToOne
    @JoinColumn(name = "avatar", columnDefinition = "VARCHAR(255) DEFAULT 00000000000000000000000000000000")
    private MediaEntity avatar;

    @Column(name = "last_update_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private Timestamp lastUpdateTime;
}
