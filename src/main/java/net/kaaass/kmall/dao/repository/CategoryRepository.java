package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
}
