package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.CartAddRequest;
import net.kaaass.kmall.dao.entity.CartEntity;
import net.kaaass.kmall.dto.CartDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService {

    CartEntity getEntityById(String id) throws NotFoundException;

    CartEntity getEntityByIdAndCheck(String id, String uid) throws NotFoundException, ForbiddenException;

    CartDto addToCart(String uid, CartAddRequest request) throws NotFoundException, BadRequestException;

    void removeFromCart(String uid, String id) throws NotFoundException, ForbiddenException;

    CartDto modifyItemCount(String uid, String id, int count) throws NotFoundException, ForbiddenException, BadRequestException;

    List<CartDto> getAllByUid(String uid, Pageable pageable);
}
