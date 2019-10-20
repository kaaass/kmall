package net.kaaass.kmall.controller;

import net.kaaass.kmall.controller.request.CartAddRequest;
import net.kaaass.kmall.dto.CartDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cart")
@PreAuthorize("hasRole('USER')")
public class UserCartController extends BaseController {

    @Autowired
    private CartService cartService;

    @PostMapping("/")
    CartDto addToCart(@RequestBody CartAddRequest request) throws NotFoundException, BadRequestException {
        return cartService.addToCart(getUid(), request);
    }

    @DeleteMapping("/{id}/")
    void removeFromCart(@PathVariable String id) throws NotFoundException, ForbiddenException {
        cartService.removeFromCart(getUid(), id);
    }

    @PostMapping("/{id}/count/")
    CartDto modifyItemCount(@PathVariable String id, @RequestParam int count) throws BadRequestException, NotFoundException, ForbiddenException {
        if (count < 1) {
            throw new BadRequestException("数量必须大于等于1！");
        }
        return cartService.modifyItemCount(getUid(), id, count);
    }

    @GetMapping("/")
    List<CartDto> getAllByUid(Pageable pageable) {
        return cartService.getAllByUid(getUid(), pageable);
    }
}
