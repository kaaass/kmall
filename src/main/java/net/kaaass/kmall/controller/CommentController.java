package net.kaaass.kmall.controller;

import net.kaaass.kmall.dao.entity.CommentEntity;
import net.kaaass.kmall.dao.repository.UserMetadataRepository;
import net.kaaass.kmall.dto.CommentDto;
import net.kaaass.kmall.dao.repository.CommentRepository;
import net.kaaass.kmall.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@PreAuthorize("permitAll()")
public class CommentController extends BaseController {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserMetadataRepository metadataRepository;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public List<CommentDto> getAllEntries(Pageable page) {
        return repository.findAllByOrderByCommentTimeDesc(page).stream()
                .map(CommentEntity::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/")
    public CommentDto findById(@PathVariable String id) throws NotFoundException {
        return repository.findById(id).map(CommentEntity::toCommentDto)
                .orElseThrow(() -> new NotFoundException("未找到评论"));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public CommentEntity addComment(@RequestBody CommentDto entry) {
        entry.setUid(getUid());
        return repository.save(entry.toCommentEntity());
    }
}
