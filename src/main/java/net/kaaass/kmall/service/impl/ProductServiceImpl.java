package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.ProductAddRequest;
import net.kaaass.kmall.controller.response.ProductCommentResponse;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dao.entity.ProductStorageEntity;
import net.kaaass.kmall.dao.repository.CategoryRepository;
import net.kaaass.kmall.dao.repository.CommentRepository;
import net.kaaass.kmall.dao.repository.OrderItemRepository;
import net.kaaass.kmall.dao.repository.ProductRepository;
import net.kaaass.kmall.dto.ProductDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.mapper.UserMapper;
import net.kaaass.kmall.service.CategoryService;
import net.kaaass.kmall.service.ProductService;
import net.kaaass.kmall.service.PromoteService;
import net.kaaass.kmall.service.UserService;
import net.kaaass.kmall.service.metadata.MetadataManager;
import net.kaaass.kmall.service.metadata.ResourceManager;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.NumericUtils;
import net.kaaass.kmall.vo.ProductExtraVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MetadataManager metadataManager;

    @Autowired
    private PromoteService promoteService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CommentRepository commentRepository;

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
    public ProductDto getById(String id) throws NotFoundException {
        return ProductMapper.INSTANCE.productEntityToDto(getEntityById(id));
    }

    @Override
    public ProductEntity getEntityById(String id) throws NotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到此商品！"));
    }

    @Override
    public ProductExtraVo getExtraById(String id, int count, String uid) throws NotFoundException {
        var extra = new ProductExtraVo();
        extra.setDetail(metadataManager.getForProduct(id, Constants.KEY_DETAIL));
        String defaultAddress = null;
        try {
            defaultAddress = userService.getDefaultAddressEntityById(uid).getId();
        } catch (NotFoundException ignored) {
        }
        var entity = getEntityById(id);
        extra.setPromotes(promoteService.getForSingleProduct(entity, count, uid, defaultAddress));
        extra.setMonthPurchase(getMonthPurchaseById(entity));
        return extra;
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

    @Override
    public List<ProductDto> getIndexItems() {
        return productRepository.findAllByIndexOrderGreaterThanEqualOrderByIndexOrderDescCreateTimeDesc(0)
                .stream()
                .map(ProductMapper.INSTANCE::productEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * 通过分类获得商品
     * @param categoryId
     * @param pageable
     * @return
     */
    @Override
    public List<ProductDto> getAllByCategory(String categoryId, Pageable pageable) throws NotFoundException {
        var root = categoryService.getEntityById(categoryId);
        var categories = categoryService.getAllSubs(root);
        log.debug("子分类：{}", categories);
        return productRepository.findAllByCategoryIn(categories, pageable)
                    .stream()
                    .map(ProductMapper.INSTANCE::productEntityToDto)
                    .collect(Collectors.toList());
    }

    @Override
    public ProductCommentResponse getComments(String id, Pageable pageable) {
        var result = new ProductCommentResponse();
        var comments = commentRepository.findAllByProductIdOrderByRateDescCommentTimeDesc(id, pageable)
                .stream()
                .map(UserMapper.INSTANCE::commentEntityToVo)
                .collect(Collectors.toList());
        var rate = commentRepository.averageRateByProductId(id)
                .map(NumericUtils::rateRound)
                .orElse(null);
        result.setComments(comments);
        result.setAverageRate(rate);
        return result;
    }

    @Override
    public List<ProductDto> search(String keyword, Pageable pageable) {
        var searchStr = Arrays.stream(keyword.split(" "))
                            .map(s -> "%" + s + "%")
                            .collect(Collectors.joining(" "));
        log.debug("字符串查找关键词 {}", searchStr);
        return productRepository.findAllByNameIsLikeOrderByIndexOrderDescCreateTimeDesc(searchStr, pageable)
                .stream()
                .map(ProductMapper.INSTANCE::productEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * 获取商品单月销售
     * @param productEntity
     * @return
     */
    private int getMonthPurchaseById(ProductEntity productEntity) {
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusMonths(1), LocalTime.MIDNIGHT));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        log.debug("查询与日期 {} 与 {} 之间", start, end);
        return orderItemRepository.sumCountByIdBetween(productEntity, start, end)
                .orElse(0);
    }
}
