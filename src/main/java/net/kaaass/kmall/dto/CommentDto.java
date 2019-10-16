package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.dao.entity.CommentEntity;
import net.kaaass.kmall.util.DateToLongSerializer;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    @Nullable
    private String id;
    @Nullable
    private String uid;
    private String title;
    private int rate;
    private String content;
    @Nullable
    @JsonSerialize(using = DateToLongSerializer.class)
    private LocalDateTime time;

    public CommentEntity toCommentEntity() {
        CommentEntity ret = new CommentEntity();
        ret.setId(id);
        ret.setUid(uid);
        ret.setRate(rate);
        ret.setTitle(title);
        ret.setContent(content);
        ret.setCommentTime(time == null ? null : Timestamp.valueOf(time));
        return ret;
    }
}
