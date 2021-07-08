package net.kaaass.kmall.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kaaass.kmall.dao.entity.ProductTemplateEntity;
import net.kaaass.kmall.dao.entity.UserAuthEntity;
import net.kaaass.kmall.dto.TemplateSchemaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class CommonTransform {

    @Autowired
    private ObjectMapper objectMapper;

    @Named("getAvatarFromAuth")
    public String getAvatarFromAuth(UserAuthEntity auth) {
        var info = auth.getUserInfo();
        if (info == null) {
            return "";
        }
        var avatar = info.getAvatar();
        if (avatar == null) {
            return "";
        }
        return avatar.getUrl();
    }

    @Named("deserializeSchema")
    public List<TemplateSchemaDto> deserializeSchema(String json) {
        try {
            if (json == null || json.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(json, new TypeReference<List<TemplateSchemaDto>>() {
            });
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Named("getTemplateId")
    public String getTemplateId(ProductTemplateEntity entity) {
        return entity == null ? null : entity.getId();
    }
}
