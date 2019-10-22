package net.kaaass.kmall.promote;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.CartAddRequest;
import net.kaaass.kmall.dto.ProductDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.mapper.PromoteMapper;
import net.kaaass.kmall.service.CartService;
import net.kaaass.kmall.service.OrderRequestContext;
import net.kaaass.kmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class OrderPromoteContextFactory {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /**
     * 从请求中建立订单打折上下文
     * @param requestContext
     * @return
     * @throws NotFoundException
     */
    public OrderPromoteContext buildFromRequestContext(OrderRequestContext requestContext) throws NotFoundException {
        var result = new OrderPromoteContext();
        var request = requestContext.getRequest();
        var products = new ArrayList<PromoteItem>();
        // 商品
        for (var cartItem : request.getCartItems()) {
            var entity = cartService.getEntityById(cartItem.getId());
            var item = PromoteMapper.INSTANCE.cartEntityToPromoteItem(entity);
            item.setPrice(item.getProduct().getPrice());
            products.add(item);
        }
        result.setProducts(products);
        // 价格
        result.setPrice(0);
        result.setMailPrice(0);
        // 用户id
        result.setUid(requestContext.getUid());
        // 地址
        result.setAddress(userService.getAddressById(request.getAddressId()));
        return result;
    }

    public OrderPromoteContext buildFromSingleProduct(ProductDto product, int count, String uid, String addressId) throws NotFoundException {
        var result = new OrderPromoteContext();
        var products = new ArrayList<PromoteItem>();
        // 商品
        var item = new PromoteItem();
        item.setProduct(product);
        item.setCount(count);
        item.setPrice(product.getPrice());
        products.add(item);
        result.setProducts(products);
        // 价格
        result.setPrice(0);
        result.setMailPrice(0);
        // 用户id
        result.setUid(uid);
        // 地址
        if (addressId != null)
            result.setAddress(userService.getAddressById(addressId));
        return result;
    }
}
