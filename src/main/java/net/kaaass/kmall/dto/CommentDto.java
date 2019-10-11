package net.kaaass.kmall.dto;

import lombok.Data;
import net.kaaass.kmall.dao.entity.CommentEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Service
public class CommentDto {
    @Nullable
    private String id;
    private String title;
    private int rate;
    private String content;
    @Nullable
    private LocalDateTime time;

    public CommentEntity toCommentEntity() {
        CommentEntity ret = new CommentEntity();
        ret.setId(id);
        ret.setRate(rate);
        ret.setTitle(title);
        ret.setContent(content);
        ret.setTime(time == null ? null : Timestamp.valueOf(time));
        return ret;
    }
}
