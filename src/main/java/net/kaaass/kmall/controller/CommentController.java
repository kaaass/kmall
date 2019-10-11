package net.kaaass.kmall.controller;

import net.kaaass.kmall.dao.entity.CommentEntity;
import net.kaaass.kmall.dto.CommentDto;
import net.kaaass.kmall.dao.repository.CommentRepository;
import net.kaaass.kmall.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository repository;

    @GetMapping("/")
    public List<CommentDto> getAllEntries(Pageable page) {
        return repository.findAllByOrderByTimeDesc(page).stream()
                .map(CommentEntity::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommentDto findById(@PathVariable String id) throws NotFoundException {
        return repository.findById(id).map(CommentEntity::toCommentDto)
                .orElseThrow(() -> new NotFoundException("未找到评论"));
    }

    @PostMapping("/")
    public CommentEntity addComment(@RequestBody CommentDto entry) {
        return repository.save(entry.toCommentEntity());
    }
}
