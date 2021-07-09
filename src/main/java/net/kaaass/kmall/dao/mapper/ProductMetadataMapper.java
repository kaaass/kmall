package net.kaaass.kmall.dao.mapper;

import net.kaaass.kmall.dao.model.ProductMetadata;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProductMetadataMapper extends Mapper<ProductMetadata> {

    @Select("select * from `product_metadata` m join `product` p on m.product_id = p.id " +
            "join `category` c on p.category_id = c.id " +
            "where m.meta_key = 'template' and c.template_id = #{template}")
    List<ProductMetadata> findAllByKeyAndTemplate(@Param("template") String templateId);
}