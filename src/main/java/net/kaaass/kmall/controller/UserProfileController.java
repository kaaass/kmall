package net.kaaass.kmall.controller;

import net.kaaass.kmall.controller.request.UserAddressRequest;
import net.kaaass.kmall.controller.request.UserInfoModifyRequest;
import net.kaaass.kmall.controller.response.UserProfileResponse;
import net.kaaass.kmall.dao.entity.UserAddressEntity;
import net.kaaass.kmall.dao.repository.UserAddressRepository;
import net.kaaass.kmall.dao.repository.UserInfoRepository;
import net.kaaass.kmall.dto.UserAddressDto;
import net.kaaass.kmall.dto.UserInfoDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.EntityCreator;
import net.kaaass.kmall.mapper.PojoMapper;
import net.kaaass.kmall.mapper.UserMapper;
import net.kaaass.kmall.service.OrderService;
import net.kaaass.kmall.service.UserService;
import net.kaaass.kmall.service.metadata.ResourceManager;
import net.kaaass.kmall.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PojoMapper pojoMapper;

    @Autowired
    private EntityCreator entityCreator;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public UserProfileResponse getUserProfile() {
        var result = new UserProfileResponse();
        var info = UserMapper.INSTANCE.userInfoEntityToDto(userInfoRepository.findByAuth(getAuthEntity()));
        result.setInfo(info);
        result.setOrderCount(orderService.getUserOrderCount(getUid()));
        return result;
    }

    @PostMapping("/")
    public UserInfoDto modifyUserProfile(@RequestBody UserInfoModifyRequest request) throws BadRequestException {
        var auth = getAuthEntity();
        var entity = userInfoRepository.findByAuth(auth);
        entity.setAuth(auth);
        var avatar = resourceManager.getEntity(request.getAvatar())
                .orElseThrow(() -> new BadRequestException("头像资源不存在！"));
        entity.setAvatar(avatar);
        entity.setWechat(request.getWechat());
        entity.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        var result = userInfoRepository.save(entity);
        return UserMapper.INSTANCE.userInfoEntityToDto(result);
    }

    /*
     * 收货地址相关
     */

    @GetMapping("/address/")
    public List<UserAddressDto> getAllAddresses() throws NotFoundException {
        return addressRepository.findAllByUserId(getUid())
                .stream()
                .map(UserMapper.INSTANCE::userAddressEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/address/{id}/")
    public UserAddressDto getAddressDtoById(@PathVariable String id) throws NotFoundException {
        return pojoMapper.entityToDto(getAddressById(id));
    }

    @PostMapping("/address/")
    public UserAddressDto addUserAddress(@Validated @RequestBody UserAddressRequest request) throws NotFoundException {
        var entity = entityCreator.createUserAddressEntity(request);
        var auth = userService.getAuthEntityById(getUid());
        entity.setUser(auth);
        entity.setLastUpdateTime(TimeUtils.nowTimestamp());
        var result = addressRepository.save(entity);
        return pojoMapper.entityToDto(result);
    }

    @PutMapping("/address/{id}/")
    public UserAddressDto editUserAddress(@PathVariable String id,
                                          @Validated @RequestBody UserAddressRequest request) throws NotFoundException {
        var oldEntity = getAddressById(id);
        var entity = entityCreator.createUserAddressEntity(request);
        var auth = userService.getAuthEntityById(getUid());
        entity.setId(id);
        entity.setUser(auth);
        entity.setLastUpdateTime(TimeUtils.nowTimestamp());
        var result = addressRepository.save(entity);
        return pojoMapper.entityToDto(result);
    }

    @DeleteMapping("/address/{id}/")
    public boolean removeUserAddress(@PathVariable String id) throws NotFoundException {
        var entity = getAddressById(id);
        addressRepository.delete(entity);
        return true;
    }

    @PostMapping("/address/{id}/default/")
    public boolean setUserDefaultAddress(@PathVariable String id) throws NotFoundException {
        var entity = getAddressById(id);
        // 设置其他为非默认、当前为默认
        var auth = entity.getUser();
        for (var addr : auth.getAddresses()) {
            addr.setDefaultAddress(addr.getId().equals(id));
            addressRepository.save(addr);
        }
        return true;
    }

    private UserAddressEntity getAddressById(String id) throws NotFoundException {
        return addressRepository.findById(id)
                // 本人收货地址
                .filter(addressEntity -> addressEntity.getUser().getId().equals(getUid()))
                .orElseThrow(() -> new NotFoundException("未找到此收货地址！"));
    }
}
