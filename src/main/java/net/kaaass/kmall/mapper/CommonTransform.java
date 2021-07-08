package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.UserAuthEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public class CommonTransform {

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
}
