package net.kaaass.kmall.controller;

import net.kaaass.kmall.dao.entity.MediaEntity;
import net.kaaass.kmall.dao.repository.MediaRepository;
import net.kaaass.kmall.dto.MediaDto;
import net.kaaass.kmall.mapper.CommenMapper;
import net.kaaass.kmall.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource")
@PreAuthorize("authenticated()")
public class ResourceController extends BaseController {

    @Autowired
    private MediaRepository mediaRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MediaDto> getAllResource(Pageable page) {
        return mediaRepository.findAllByOrderByUploadTimeDesc(page)
                .stream()
                .map(CommenMapper.INSTANCE::mediaEntityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public MediaDto addNetworkResource(@RequestParam String url, @RequestParam String type) {
        var entity = new MediaEntity();
        entity.setType(type);
        entity.setUrl(url);
        entity.setUploaderUid(getUid());
        var result = mediaRepository.save(entity);
        return CommenMapper.INSTANCE.mediaEntityToDto(result);
    }
}
