package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    List<CommentEntity> findAllByOrderByCommentTimeDesc(Pageable page);

    List<CommentEntity> findAllByProductIdOrderByRateDescCommentTimeDesc(String productId, Pageable page);
}
