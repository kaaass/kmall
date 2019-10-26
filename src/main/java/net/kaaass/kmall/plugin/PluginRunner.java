package net.kaaass.kmall.plugin;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class PluginRunner implements ApplicationRunner {

    @Autowired
    private PluginService pluginService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始启动模组加载");
        pluginService.mountAll();
    }
}
