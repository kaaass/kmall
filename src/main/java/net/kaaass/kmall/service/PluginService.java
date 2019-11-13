package net.kaaass.kmall.service;

import net.kaaass.kmall.dto.PluginDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;

import java.util.List;

public interface PluginService {

    List<PluginDto> getAll();

    void mountAll();

    PluginDto enable(String path) throws BadRequestException;

    void disable(String id) throws NotFoundException, BadRequestException;

    void remove(String id) throws NotFoundException;
}
