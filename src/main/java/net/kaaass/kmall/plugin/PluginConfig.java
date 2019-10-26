package net.kaaass.kmall.plugin;

import lombok.Data;
import net.kaaass.kmall.eventhandle.EventPriority;

import java.util.ArrayList;
import java.util.List;

@Data
public class PluginConfig {

    @Data
    public static class ListenerDescriptor {

        private String event;

        private EventPriority priority;

        private String file;
    }

    private List<ListenerDescriptor> listeners = new ArrayList<>();
}
