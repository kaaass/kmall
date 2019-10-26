package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.KmallApplication;
import net.kaaass.kmall.dao.entity.PluginEntity;
import net.kaaass.kmall.dao.repository.PluginRepository;
import net.kaaass.kmall.dto.PluginDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.CommenMapper;
import net.kaaass.kmall.plugin.PluginManager;
import net.kaaass.kmall.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        var entities = pluginRepository.findAll();
        for (var entity : entities) {
            log.debug("开始加载插件 entity = {}", entity);
            pluginManager.loadPlugin(entity.getId(), entity.getFilename());
        }
    }

    @Override
    public PluginDto enable(String path) {
        var entity = new PluginEntity();
        entity.setFilename(path);
        return CommenMapper.INSTANCE.pluginEntityToDto(pluginRepository.save(entity));
    }

    @Override
    public void disable(String id) throws NotFoundException {
        var entity = pluginRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("未找到此模组"));
        pluginManager.unloadPlugin(entity.getId());
        pluginRepository.delete(entity);
    }
}
