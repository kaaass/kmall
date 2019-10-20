package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.ProductAddRequest;
import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dao.entity.ProductStorageEntity;
import net.kaaass.kmall.dao.repository.CategoryRepository;
import net.kaaass.kmall.dao.repository.ProductRepository;
import net.kaaass.kmall.dto.ProductDto;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.service.ProductService;
import net.kaaass.kmall.service.metadata.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 增加商品
     *
     * @param productToAdd
     * @return
     */
    @Override
    public Optional<ProductDto> addProduct(ProductAddRequest productToAdd) {
        var entity = new ProductEntity();
        entity.setName(productToAdd.getName());
        entity.setPrice(productToAdd.getPrice());
        entity.setMailPrice(productToAdd.getMailPrice());
        entity.setBuyLimit(productToAdd.getBuyLimit());
        var storage = new ProductStorageEntity();
        storage.setRest(productToAdd.getRest());
        entity.setStorage(storage);
        try {
            var thumbnail = resourceManager.getEntity(productToAdd.getThumbnailId()).orElseThrow();
            entity.setThumbnail(thumbnail);
            var category = categoryRepository.findById(productToAdd.getCategoryId()).orElseThrow();
            entity.setCategory(category);
            return Optional.of(productRepository.save(entity))
                    .map(ProductMapper.INSTANCE::productEntityToDto);
        } catch (Exception e) {
            log.info("插入时发生错误", e);
            return Optional.empty();
        }
    }

    /**
     * 从id获取商品
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ProductDto> getById(String id) {
        return productRepository.findById(id)
                .map(ProductMapper.INSTANCE::productEntityToDto);
    }

    /**
     * 获取全部商品
     *
     * @param pageable
     * @return
     */
    @Override
    public List<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .stream()
                .map(ProductMapper.INSTANCE::productEntityToDto)
                .collect(Collectors.toList());
    }
}
