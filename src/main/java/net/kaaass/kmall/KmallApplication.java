package net.kaaass.kmall;

import net.kaaass.kmall.eventhandle.EventBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KmallApplication {

    public static final EventBus EVENT_BUS = new EventBus();

    public static void main(String[] args) {
        SpringApplication.run(KmallApplication.class, args);
    }

}
