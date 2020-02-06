package com.lind.xbootdemo.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class Subscriber {


    /**
     * lindqueue.
     *
     * @param data .
     */
    @RabbitListener(queues = MqConfig.LIND_QUEUE_ROUTEKEY1)
    public void lindQueue(String data) {
        if (LocalDateTime.now().getSecond() != 15) {
            throw new IllegalArgumentException("故意抛出来的异常1!");
        } else {
            log.info("LIND_QUEUE从队列拿到数据 ：{}", data);
        }
    }

    /**
     * cold queue.
     *
     * @param data .
     */
    @RabbitListener(queues = MqConfig.LIND_QUEUE_FANOUT)
    public void lindQueue2(String data) {
        log.info("广播消息消费者 ：{}", data);
    }

}
