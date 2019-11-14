package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.CartAddRequest;
import net.kaaass.kmall.dao.entity.CartEntity;
import net.kaaass.kmall.dao.repository.CartRepository;
import net.kaaass.kmall.dto.CartDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.service.CartService;
import net.kaaass.kmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public CartEntity getEntityById(String id) throws NotFoundException {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("该项目不在购物车内！"));
    }

    @Override
    public CartEntity getEntityByIdAndCheck(String id, String uid) throws NotFoundException, ForbiddenException {
        var result = this.getEntityById(id);
        if (!result.getUid().equals(uid)) {
            throw new ForbiddenException("该项目不在购物车内！");
        }
        return result;
    }

    @Override
    public CartDto addToCart(String uid, CartAddRequest request) throws NotFoundException, BadRequestException {
        var product = productService.getEntityById(request.getProductId());
        var entity = cartRepository.findByProductAndUid(product, uid)
                        .orElseGet(() -> {
                            var newEntity = new CartEntity();
                            newEntity.setUid(uid);
                            newEntity.setProduct(product);
                            newEntity.setCount(0);
                            return newEntity;
                        });
        // 检查购买限制
        var limit = product.getBuyLimit();
        var dest = entity.getCount() + request.getCount();
        if (limit != -1 && dest > limit) {
            throw new BadRequestException(String.format("本商品限购%d件！", limit));
        } else {
            entity.setCount(dest);
        }
        return ProductMapper.INSTANCE.cartEntityToDto(cartRepository.save(entity));
    }

    @Override
    public void removeFromCart(String uid, String id) throws NotFoundException, ForbiddenException {
        var entity = this.getEntityByIdAndCheck(id, uid);
        cartRepository.delete(entity);
    }

    @Override
    public CartDto modifyItemCount(String uid, String id, int count) throws NotFoundException, ForbiddenException, BadRequestException {
        var entity = this.getEntityByIdAndCheck(id, uid);
        var product = entity.getProduct();
        // 检查购买限制
        var limit = product.getBuyLimit();
        if (limit != -1 && count > limit) {
            throw new BadRequestException(String.format("本商品限购%d件！", limit));
        } else {
            entity.setCount(count);
        }
        return ProductMapper.INSTANCE.cartEntityToDto(cartRepository.save(entity));
    }

    @Override
    public List<CartDto> getAllByUid(String uid, Pageable pageable) {
        return cartRepository.findAllByUidOrderByCreateTimeDesc(uid, pageable)
                .stream()
                .map(ProductMapper.INSTANCE::cartEntityToDto)
                .collect(Collectors.toList());
    }
}
