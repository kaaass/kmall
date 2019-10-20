package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.CategoryAddRequest;
import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dto.CategoryDto;
import net.kaaass.kmall.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryDto> add(CategoryAddRequest userToAdd);

    CategoryDto getById(String id) throws NotFoundException;

    CategoryEntity getEntityById(String id) throws NotFoundException;

    List<CategoryDto> getAll(Pageable pageable);

    /**
     * 寻找某个分类的所有子结点
     * @param root
     * @return
     */
    List<CategoryEntity> getAllSubs(CategoryEntity root);
}
