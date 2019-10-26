package net.kaaass.kmall.controller;

import net.kaaass.kmall.dto.PluginDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plugin")
@PreAuthorize("hasRole('ADMIN')")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @GetMapping("/")
    List<PluginDto> getAll() {
        return pluginService.getAll();
    }

    @PostMapping("/")
    PluginDto enable(@RequestParam String path) {
        return pluginService.enable(path);
    }

    @PostMapping("/{id}/disable/")
    void disable(@PathVariable String id) throws NotFoundException {
        pluginService.disable(id);
    }
}
