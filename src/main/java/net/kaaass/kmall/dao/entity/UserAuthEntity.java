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
@Table(name = "user_auth")
public class UserAuthEntity {
    @Id
    @GenericGenerator(name = Constants.ID_GENERATOR, strategy = Constants.UUID)
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private String id;

    @Column(name = "phone",
            length = 20,
            unique = true)
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @Column(name = "enable",
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    boolean enable = true;

    @Column(name = "validate",
            columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean validate = false;

    @Column(name = "register_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp registerTime;

    @Column(name = "last_login_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastLoginTime;
}
