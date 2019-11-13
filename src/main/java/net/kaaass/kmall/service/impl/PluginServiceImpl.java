package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.KmallApplication;
import net.kaaass.kmall.dao.entity.PluginEntity;
import net.kaaass.kmall.dao.repository.PluginRepository;
import net.kaaass.kmall.dto.PluginDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.CommenMapper;
import net.kaaass.kmall.plugin.PluginManager;
import net.kaaass.kmall.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PluginServiceImpl implements PluginService {

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private PluginManager pluginManager;

    @Override
    public List<PluginDto> getAll() {
        return pluginRepository.findAll()
                .stream()
                .map(CommenMapper.INSTANCE::pluginEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void mountAll() {
        log.debug("开始加载所有插件");
        var entities = pluginRepository.findAllByEnableTrue();
        for (var entity : entities) {
            log.debug("开始加载插件 entity = {}", entity);
            pluginManager.loadPlugin(entity.getId(), entity.getFilename());
        }
    }

    @Override
    public PluginDto enable(String path) throws BadRequestException {
        var entity = pluginRepository.findFirstByFilename(path)
                .orElseGet(() -> {
                    var newEntity = new PluginEntity();
                    newEntity.setFilename(path);
                    return newEntity;
                });
        if (entity.isEnable()) {
            throw new BadRequestException("该插件已经启用！");
        }
        entity.setEnable(true);
        entity.setEnableTime(Timestamp.valueOf(LocalDateTime.now()));
        entity = pluginRepository.save(entity);
        log.debug("启用插件 entity = {}", entity);
        pluginManager.loadPlugin(entity.getId(), entity.getFilename());
        return CommenMapper.INSTANCE.pluginEntityToDto(pluginRepository.save(entity));
    }

    @Override
    public void disable(String id) throws NotFoundException, BadRequestException {
        var entity = pluginRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到此模组"));
        if (!entity.isEnable()) {
            throw new BadRequestException("该插件已经禁用！");
        }
        entity.setEnable(false);
        pluginRepository.save(entity);
        pluginManager.unloadPlugin(entity.getId());
    }

    @Override
    public void remove(String id) throws NotFoundException {
        var entity = pluginRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到此模组"));
        if (entity.isEnable())
            pluginManager.unloadPlugin(entity.getId());
        pluginRepository.delete(entity);
    }
}
