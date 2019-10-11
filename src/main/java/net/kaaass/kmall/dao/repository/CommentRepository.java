package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    public abstract List<CommentEntity> findAllByOrderByTimeDesc(Pageable page);
}
