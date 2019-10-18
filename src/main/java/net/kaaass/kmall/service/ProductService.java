package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.ProductAddRequest;
import net.kaaass.kmall.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> addProduct(ProductAddRequest userToAdd);

    Optional<ProductDto> getById(String id);

    List<ProductDto> getAll(Pageable pageable);
}
