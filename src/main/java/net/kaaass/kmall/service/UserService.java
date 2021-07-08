package net.kaaass.kmall.service;

import net.kaaass.kmall.dao.entity.UserAddressEntity;
import net.kaaass.kmall.dao.entity.UserAuthEntity;
import net.kaaass.kmall.dto.UserAddressDto;
import net.kaaass.kmall.dto.UserInfoDto;
import net.kaaass.kmall.exception.NotFoundException;

import java.util.List;

public interface UserService {

    /**
     * @deprecated
     */
    UserAuthEntity getAuthEntityById(String uid) throws NotFoundException;

    UserAddressEntity getAddressEntityById(String id) throws NotFoundException;

    UserAddressEntity getAddressEntityByIdAndCheck(String id, String uid) throws NotFoundException;

    UserAddressDto getAddressById(String id) throws NotFoundException;

    UserAddressEntity getDefaultAddressEntityById(String id) throws NotFoundException;

    List<UserInfoDto> getAllUser();
}
