package net.kaaass.kmall;

import net.kaaass.kmall.eventhandle.EventBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("net.kaaass.kmall.dao.mapper")
public class KmallApplication {

    public static final EventBus EVENT_BUS = new EventBus();

    public static void main(String[] args) {
        SpringApplication.run(KmallApplication.class, args);
    }

}
