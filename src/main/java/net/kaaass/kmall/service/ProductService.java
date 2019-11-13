package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.ProductAddRequest;
import net.kaaass.kmall.controller.response.ProductCommentResponse;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dto.ProductDto;
import net.kaaass.kmall.exception.InternalErrorExeption;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.vo.ProductExtraVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> addProduct(ProductAddRequest userToAdd);

    ProductDto editProduct(String id, ProductAddRequest userToAdd) throws NotFoundException, InternalErrorExeption;

    void removeProduct(String id);

    ProductDto getById(String id) throws NotFoundException;

    ProductEntity getEntityById(String id) throws NotFoundException;

    ProductExtraVo getExtraById(String id, int count, String uid) throws NotFoundException;

    List<ProductDto> getAll(Pageable pageable);

    List<ProductDto> getIndexItems();

    List<ProductDto> getQuickBuyItems();

    List<ProductDto> getAllByCategory(String categoryId, Pageable pageable) throws NotFoundException;

    ProductCommentResponse getComments(String id, Pageable pageable);

    List<ProductDto> search(String keyword, Pageable pageable);
}
