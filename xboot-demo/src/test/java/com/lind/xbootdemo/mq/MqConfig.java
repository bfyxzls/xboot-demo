package com.lind.xbootdemo.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

/**
 * 定义普通交换机,普通队列,并进行绑定.
 */
@Component
@ActiveProfiles("test")
public class MqConfig {
    public static final String LIND_EXCHANGE = "test.basic.exchange";
    public static final String LIND_TEST_DEAL_QUEUE = "test.basic.deal.queue";
    public static final String LIND_FANOUT_EXCHANGE = "test.basic.fanoutExchange";
    public static final String LIND_QUEUE_ROUTEKEY1 = "test.basic.a1";
    public static final String LIND_QUEUE_FANOUT = "test.basic.a2";

    /**
     * 单位为毫秒.
     */
    private static final long makeCallExpire = 1000;

    /**
     * 创建普通交换机.
     */
    @Bean
    public TopicExchange lindExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange(LIND_EXCHANGE).durable(true)
                .build();
    }


    /**
     * 创建普通队列，超时后发到死信队列.
     */
    @Bean
    public Queue lindNeedDealLetterQueue() {
        return QueueBuilder.durable(LIND_TEST_DEAL_QUEUE)
                .withArgument("x-dead-letter-exchange", DealLetterSubscriber.LIND_DL_EXCHANGE)//设置死信交换机
                .withArgument("x-message-ttl", makeCallExpire)
                .withArgument("x-dead-letter-routing-key", DealLetterSubscriber.LIND_DEAD_QUEUE)//设置死信routingKey
                .build();
    }


    /**
     * 绑定普通队列.
     *
     * @return
     */
    @Bean
    public Binding bindBuilders() {
        return BindingBuilder.bind(lindNeedDealLetterQueue())
                .to(lindExchange())
                .with(LIND_TEST_DEAL_QUEUE);
    }

    /**
     * 创建普通队列.
     *
     * @return
     */
    @Bean
    public Queue key1() {
        return new Queue(LIND_QUEUE_ROUTEKEY1);
    }

    /**
     * 创建普通队列.
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(LIND_QUEUE_FANOUT);
    }

    /**
     * bind1.
     *
     * @return
     */
    @Bean
    public Binding bindBuildersRouteKey1() {
        return BindingBuilder.bind(key1())
                .to(lindExchange())
                .with(LIND_QUEUE_ROUTEKEY1);
    }

    /**
     * bind2.
     *
     * @return
     */
    @Bean
    public Binding bindBuildersRouteKey2() {
        return BindingBuilder.bind(fanoutQueue())
                .to(fanoutExchange());
    }

    /**
     * 广播交换机.
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(LIND_FANOUT_EXCHANGE);
    }

}
