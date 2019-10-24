package net.kaaass.kmall.service.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.conf.RabbitConfig;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.OrderRequestContext;
import net.kaaass.kmall.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_ORDER)
@Slf4j
public class OrderMessageListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitHandler
    public void process(String content) {
        log.debug("开始处理订单消息 {}", content);
        OrderRequestContext context = null;
        try {
            context = this.objectMapper.readValue(content, OrderRequestContext.class);
        } catch (IOException e) {
            log.warn("订单消息反序列化错误", e);
        }
        try {
            orderService.doCreate(context);
        } catch (Exception e) {
            log.warn("订单消息处理错误", e);
        }
    }
}
