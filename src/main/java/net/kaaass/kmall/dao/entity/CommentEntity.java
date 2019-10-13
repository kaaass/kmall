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

    @Column(name = "uid")
    private String uid;

    @Column(name = "title")
    private String title;

    @Column(name = "rate")
    private int rate;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_time",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp commentTime;

    public CommentDto toCommentDto() {
        CommentDto ret = new CommentDto();
        ret.setId(id);
        ret.setUid(uid);
        ret.setRate(rate);
        ret.setTitle(title);
        ret.setContent(content);
        ret.setTime(commentTime.toLocalDateTime());
        return ret;
    }
}
