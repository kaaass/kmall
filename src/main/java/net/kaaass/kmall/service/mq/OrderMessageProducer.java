package net.kaaass.kmall.service.mq;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.conf.RabbitConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMessageProducer implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMessage(String key, String content) {
        CorrelationData correlationId = new CorrelationData(key);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_ORDER, RabbitConfig.ROUTING_KEY_ORDER, content, correlationId);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.debug("订单消息 {} 处理成功", correlationData);
        } else {
            log.warn("订单消息 {} 处理失败，原因 {}", correlationData, cause);
        }
    }
}
