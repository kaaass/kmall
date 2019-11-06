package net.kaaass.kmall.controller.response;

import lombok.Data;
import net.kaaass.kmall.vo.CommentVo;

import java.util.List;

/**
 * 物品评论请求返回
 */
@Data
public class ProductCommentResponse {

    private Float averageRate;

    private List<CommentVo> comments;
}
