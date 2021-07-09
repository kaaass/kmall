package net.kaaass.kmall.dao.mapper;

import net.kaaass.kmall.dao.model.Comments;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface CommentsMapper extends Mapper<Comments> {

    @Select("select avg(`rate`) from `comments` where `product_id` = #{productId}")
    float averageRateByProductId(@Param("productId") String productId);
}