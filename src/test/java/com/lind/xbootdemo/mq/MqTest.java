package com.lind.xbootdemo.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MqTest {
    @Autowired
    Publisher publisher;

    /**
     * 将会把消息发送给订阅了publisher.routekey的所有消费者.
     *
     * @throws Exception .
     */
    @Test
    public void publisherTest() throws Exception {
        publisher.publish("hello lind");
        TimeUnit.MILLISECONDS.sleep(6000);//耗时3分钟
    }

    /**
     * 广播消息.     *
     *
     * @throws Exception
     */
    @Test
    public void fanoutPublishTest() throws Exception {
        publisher.fanoutPublish("hello lind");
        TimeUnit.MILLISECONDS.sleep(1000);//耗时1s
    }

    /**
     * 测试消息超时由死信消费.
     *
     * @throws Exception .
     */
    @Test
    public void publisherDealTest() throws Exception {
        publisher.publishDealLetter("hello lind");
        TimeUnit.MILLISECONDS.sleep(6000);//耗时3分钟
    }

}
