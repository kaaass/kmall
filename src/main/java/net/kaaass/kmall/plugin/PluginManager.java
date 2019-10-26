package net.kaaass.kmall.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.KmallApplication;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class PluginManager {

    private static Set<String> idSet = new HashSet<>();

    @Autowired
    private ObjectMapper objectMapper;

    public void loadPlugin(String id, String path) {
        idSet.add(id);
        log.debug("加载插件 id = {}, path = {}", id, path);
        try {
            var listenersMap = loadListenerPaths(path);
            loadListeners(id, listenersMap, path);
        } catch (IOException e) {
            log.warn("插件监听器加载错误！", e);
        }
    }

    public void unloadPlugin(String id) {
        log.debug("卸载插件 id = {}", id);
        KmallApplication.EVENT_BUS.unregister(id);
        idSet.remove(id);
    }

    /**
     * 返回事件类与处理文件的相对地址
     * @param path
     * @return
     * @throws IOException
     */
    private List<PluginConfig.ListenerDescriptor> loadListenerPaths(String path) throws IOException {
        String configStr = FileUtils.readAll(path + Constants.PLUGIN_PATH_CONFIG);
        var config = objectMapper.readValue(configStr, PluginConfig.class);
        log.debug("加载路径 {} 配置 {}", path, config);
        return config.getListeners();
    }

    private void loadListeners(String id, List<PluginConfig.ListenerDescriptor> listeners, String pluginBase) throws IOException {
        for (var descriptor : listeners) {
            log.debug("注册js事件：{}", descriptor);
            var codeStr = FileUtils.readAll(pluginBase + descriptor.getFile());
            KmallApplication.EVENT_BUS.register(id, descriptor.getEvent(), descriptor.getPriority(), codeStr);
        }
    }
}
