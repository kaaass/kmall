package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.ProductAddRequest;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dto.ProductDto;
import net.kaaass.kmall.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> addProduct(ProductAddRequest userToAdd);

    ProductDto getById(String id) throws NotFoundException;

    ProductEntity getEntityById(String id) throws NotFoundException;

    List<ProductDto> getAll(Pageable pageable);

    List<ProductDto> getAllByCategory(String categoryId, Pageable pageable) throws NotFoundException;
}
