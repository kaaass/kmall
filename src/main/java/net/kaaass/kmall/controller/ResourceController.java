package net.kaaass.kmall.controller;

import net.kaaass.kmall.dao.entity.MediaEntity;
import net.kaaass.kmall.dao.repository.MediaRepository;
import net.kaaass.kmall.dto.MediaDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.mapper.CommenMapper;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource")
@PreAuthorize("isAuthenticated()")
public class ResourceController extends BaseController {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

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

    @PutMapping("/image/")
    public MediaDto uploadImage(@RequestParam MultipartFile file) throws BadRequestException, IOException {
        if (file.isEmpty()) {
            throw new BadRequestException("无文件传送！");
        }

        var originalFilename = file.getOriginalFilename();
        var suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        var newFileName = StringUtils.uuid() + "." + suffix;
        // TODO 检查suffix

        File destFile = new File(uploadFolder + newFileName);
        file.transferTo(destFile);

        var entity = new MediaEntity();
        entity.setType(Constants.MEDIA_TYPE_IMAGE);
        entity.setUrl("/upload/" + newFileName);
        entity.setUploaderUid(getUid());

        var result = mediaRepository.save(entity);
        return CommenMapper.INSTANCE.mediaEntityToDto(result);
    }
}
