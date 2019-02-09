package com.wei.rabbitmq;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



        import org.springframework.amqp.core.*;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

        import java.util.HashMap;
        import java.util.Map;

/**
 * @author 为为
 * @create 2/7
 */
@Configuration
public class MqConfig {

    public static final String MIAOSHA_QUEUE = "miaosha.queue";
//    public static final String FANOUT_EXCHANGE = "fanout";
//    public static final String TOPIC_EXCHANGE = "topic";
//    public static final String HEADERS_EXCHANGE = "headers";
//    public static final String FANOUT_QUEUE1 = "fanout.queue1";
//    public static final String FANOUT_QUEUE2 = "fanout.queue2";
//    public static final String TOPIC_QUEUE1 = "topic.queue1";
//    public static final String TOPIC_QUEUE2 = "topic.queue2";
//    public static final String HEADERS_QUEUE1 = "headers.queue1";
//    public static final String HEADERS_QUEUE2 = "headers.queue2";

    @Bean
    public Queue mioashaQueue(){
        return new Queue(MIAOSHA_QUEUE);
    }


//    @Bean
//    public Queue queue(){
//        return  new Queue(QUEUE,true);
//    }
//
//
//
//    /**
//     *广播式交换机
//     * @return
//     */
//    @Bean
//    public Queue fanoutQueue1(){
//        return  new Queue(FANOUT_QUEUE1,true);
//    } @Bean
//    public Queue fanoutQueue2(){
//        return  new Queue(FANOUT_QUEUE2,true);
//    }
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return  new FanoutExchange(FANOUT_EXCHANGE);
//    }
//    @Bean
//    public Binding fanoutBinding1(){
//        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding fanoutBinding2(){
//        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
//    }
//
//    /**
//     * 通配符式交换机
//     * @return
//     */
//    @Bean
//    public Queue topicQueue1(){
//        return  new Queue(TOPIC_QUEUE1,true);
//    } @Bean
//    public Queue topicQueue2(){
//        return  new Queue(TOPIC_QUEUE2,true);
//    }
//    @Bean
//    public TopicExchange topicExchange(){
//        return  new TopicExchange(TOPIC_EXCHANGE);
//    }
//    @Bean
//    public Binding topicBinding1(){
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.#");
//    }
//    @Bean
//    public Binding topicBinding2(){
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.key1");
//    }
//    /**
//     *hedners
//     */
//    @Bean
//    public Queue headersQueue1(){
//        return  new Queue(HEADERS_QUEUE1,true);
//    } @Bean
//    public Queue headersQueue2(){
//        return  new Queue(HEADERS_QUEUE2,true);
//    }
//    @Bean
//    public HeadersExchange headersExchange(){
//        return  new HeadersExchange(HEADERS_EXCHANGE);
//    }
//    @Bean
//    public Binding hendersBinding1(){
//        Map<String,Object> map = new HashMap(16);
//        map.put("header1","values1");
//        map.put("header2","values2");
//        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).whereAll(map).match();
//    }
}
