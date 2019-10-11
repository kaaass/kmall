package net.kaaass.kmall.dao.entity;

import lombok.Data;
import net.kaaass.kmall.dto.CommentDto;
import net.kaaass.kmall.util.Constants;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GenericGenerator(name = Constants.ID_GENERATOR, strategy = Constants.UUID)
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "rate")
    private int rate;

    @Column(name = "content")
    private String content;

    @Column(name = "time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp time;

    public CommentDto toCommentDto() {
        CommentDto ret = new CommentDto();
        ret.setId(id);
        ret.setRate(rate);
        ret.setTitle(title);
        ret.setContent(content);
        ret.setTime(time.toLocalDateTime());
        return ret;
    }
}
