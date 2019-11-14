package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.dao.entity.UserAddressEntity;
import net.kaaass.kmall.dao.repository.UserAddressRepository;
import net.kaaass.kmall.dao.repository.UserInfoRepository;
import net.kaaass.kmall.dto.UserAddressDto;
import net.kaaass.kmall.dto.UserInfoDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.UserMapper;
import net.kaaass.kmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserAddressEntity getAddressEntityById(String id) throws NotFoundException {
        return userAddressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到该地址！"));
    }

    @Override
    public UserAddressEntity getAddressEntityByIdAndCheck(String id, String uid) throws NotFoundException {
        return userAddressRepository.findById(id)
                .filter(addressEntity -> addressEntity.getUid().equals(uid))
                .orElseThrow(() -> new NotFoundException("未找到该地址！"));
    }

    @Override
    public UserAddressDto getAddressById(String id) throws NotFoundException {
        return UserMapper.INSTANCE.userAddressEntityToDto(getAddressEntityById(id));
    }

    @Override
    public UserAddressEntity getDefaultAddressEntityById(String id) throws NotFoundException {
        return userAddressRepository.findFirstByUidAndDefaultAddressTrue(id)
                .orElseThrow(() -> new NotFoundException("未找到该地址！"));
    }

    @Override
    public List<UserInfoDto> getAllUser() {
        return userInfoRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::userInfoEntityToDto)
                .collect(Collectors.toList());
    }
}
