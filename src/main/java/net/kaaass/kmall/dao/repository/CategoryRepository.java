package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    List<CategoryEntity> findAllByParent(CategoryEntity parent);
}
