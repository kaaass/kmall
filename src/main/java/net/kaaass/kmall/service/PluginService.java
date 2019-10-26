package net.kaaass.kmall.service;

import net.kaaass.kmall.dto.PluginDto;
import net.kaaass.kmall.exception.NotFoundException;

import java.util.List;

public interface PluginService {

    List<PluginDto> getAll();

    void mountAll();

    PluginDto enable(String path);

    void disable(String id) throws NotFoundException;
}
