package com.wei.rabbitmq;

import com.wei.rabbitmq.MiaoshaMessage;
import com.wei.rabbitmq.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wei.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 为为
 * @create 2/7
 */
@Service
public class MqSender {

    @Autowired
    RedisService redisService;

    @Autowired
    AmqpTemplate amqpTemplate;

    public void senderMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String msg = redisService.beanToString(miaoshaMessage);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE, msg);
    }

//    public void sender(Object obj) {
//        String msg = redisService.beanToString(obj);
//        amqpTemplate.convertAndSend(MqConfig.QUEUE, msg);
//        System.out.println("sender direct :" + msg);
//    }
//
//    /**
//     * @param obj
//     */
//    public void fanoutSender(Object obj) {
//        String msg = redisService.beanToString(obj);
//        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE, "", msg);
//        System.out.println("sender fanout :" + msg);
//    }
//
//    /**
//     * topic
//     *
//     * @param obj
//     */
//    public void topicSender(Object obj) {
//        String msg = redisService.beanToString(obj);
//        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
//        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
//        System.out.println("sender topic :" + msg);
//    }
//
//    /**
//     * headers
//     *
//     * @param obj
//     */
//    public void hendersSender(Object obj) {
//        String msg = redisService.beanToString(obj);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("header1", "values1");
//        properties.setHeader("header2", "values2");
//        Message message = new Message(msg.getBytes(), properties);
//
//        amqpTemplate.convertAndSend(MqConfig.HEADERS_EXCHANGE, "", message);
//        System.out.println("sender henders :" + msg);
//    }

}
