package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.CategoryAddRequest;
import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dao.repository.CategoryRepository;
import net.kaaass.kmall.dto.CategoryDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<CategoryDto> add(CategoryAddRequest categoryDto) {
        var entity = new CategoryEntity();
        entity.setName(categoryDto.getName());
        var parent = Optional.ofNullable(categoryDto.getParentId())
                .flatMap(categoryRepository::findById)
                .orElse(null);
        entity.setParent(parent);
        try {
            return Optional.of(categoryRepository.save(entity))
                    .map(ProductMapper.INSTANCE::categoryEntityToDto);
        } catch (Exception e) {
            log.info("插入时发生错误", e);
            return Optional.empty();
        }
    }

    @Override
    public CategoryDto getById(String id) throws NotFoundException {
        return categoryRepository.findById(id)
                .map(ProductMapper.INSTANCE::categoryEntityToDto)
                .orElseThrow(() -> new NotFoundException("未找到此分类！"));
    }

    @Override
    public CategoryEntity getEntityById(String id) throws NotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到此分类！"));
    }

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(ProductMapper.INSTANCE::categoryEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryEntity> getAllSubs(CategoryEntity root) {
        var result = new ArrayList<CategoryEntity>(){{
            add(root);
        }};
        // 找到一层子节点
        var sons = categoryRepository.findAllByParent(root);
        for (var son : sons) {
            result.addAll(getAllSubs(son));
        }
        return result;
    }
}
