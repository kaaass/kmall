package net.kaaass.kmall;

import net.kaaass.kmall.event.BeforeControllerEvent;
import net.kaaass.kmall.eventhandle.EventBus;
import net.kaaass.kmall.eventhandle.EventPriority;
import net.kaaass.kmall.eventhandle.SubscribeEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KmallApplication {

    public static final EventBus EVENT_BUS = new EventBus();

    public static void main(String[] args) {
        SpringApplication.run(KmallApplication.class, args);
    }

}
