package net.kaaass.kmall.controller;

import net.kaaass.kmall.dao.repository.UserAddressRepository;
import net.kaaass.kmall.dto.UserAddressDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/profile")
@PreAuthorize("hasRole('USER')")
public class UserProfileController extends BaseController {

    @Autowired
    private UserAddressRepository addressRepository;

    /*
     * 收货地址相关
     */

    @GetMapping("/address/all")
    public List<UserAddressDto> getAllAddresses() throws NotFoundException {
        return addressRepository.findAllByUid(getUid())
                .stream()
                .map(UserMapper.INSTANCE::userAddressEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/address/{id}")
    public UserAddressDto getAddressDtoById(@PathVariable String id) throws NotFoundException {
        return addressRepository.findById(id)
                .filter(addressEntity -> addressEntity.getUid().equals(getUid())) // 本人收货地址
                .map(UserMapper.INSTANCE::userAddressEntityToDto)
                .orElseThrow(() -> new NotFoundException("未找到此收货地址！"));
    }

    @PostMapping("/address")
    public UserAddressDto addUserAddress(@RequestBody UserAddressDto userAddressDto) {
        // TODO validate,去重
        var entity = UserMapper.INSTANCE.userAddressDtoToEntity(userAddressDto);
        entity.setUid(getUid());
        entity.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        var result = addressRepository.save(entity);
        return UserMapper.INSTANCE.userAddressEntityToDto(result);
    }
}
