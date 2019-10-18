package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.CategoryAddRequest;
import net.kaaass.kmall.dto.CategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryDto> add(CategoryAddRequest userToAdd);

    Optional<CategoryDto> getById(String id);

    List<CategoryDto> getAll(Pageable pageable);
}
