package com.example.springboot.rabbitmq.producers.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

//    public static final String EXCHANGE_NAME = "boot_topic_exchange";
//    public static final String QUEUE_NAME = "boot_queue";
//
//    //1、Create exchange
//    @Bean("bootExchange")
//    public Exchange bootExchange() {
//        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();  //持久化:true
//    }
//
//    //2、Create Queue
//    @Bean("bootQueue")
//    public Queue bootQueue() {
//        return QueueBuilder.durable(QUEUE_NAME).build();
//    }
//
//    //3、Binding Queue and exchange
//    @Bean
//    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();//noargs 代表不需要指定參數
//    }




//    public static final String EXCHANGE_NAME = "test_exchange_confirm";
//    public static final String QUEUE_NAME = "test_queue_confirm";
//
//    //1、Create exchange
//    @Bean("test_exchange_confirm")
//    public Exchange bootExchange() {
//        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();  //持久化:true
//    }
//
//    //2、Create Queue
//    @Bean("test_queue_confirm")
//    public Queue bootQueue() {
//        return QueueBuilder.durable(QUEUE_NAME).build();
//    }
//
//    //3、Binding Queue and exchange
//    @Bean
//    public Binding bindQueueExchange(@Qualifier("test_queue_confirm") Queue queue, @Qualifier("test_exchange_confirm") Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("#.confirm").noargs();//noargs 代表不需要指定參數
//    }



//    public static final String EXCHANGE_NAME = "test_exchange_ttl";
//    public static final String QUEUE_NAME = "test_queue_ttl";
//
//    //1、Create exchange
//    @Bean("test_exchange_ttl")
//    public Exchange bootExchange() {
//        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();  //持久化:true
//    }
//
//    //2、Create Queue
//    @Bean("test_queue_ttl")
//    public Queue bootQueue() {
//        final HashMap<String, Object> arguments
//                = new HashMap<>();
//        //设置TTL设置10秒过期
//        arguments.put("x-message-ttl",10000);
//
//        return QueueBuilder.durable(QUEUE_NAME).withArguments(arguments).build();
//    }
//
//    //3、Binding Queue and exchange
//    @Bean
//    public Binding bindQueueExchange(@Qualifier("test_queue_ttl") Queue queue, @Qualifier("test_exchange_ttl") Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("ttl.#").noargs();//noargs 代表不需要指定參數
//    }


    /**
     *
     * 死信隊列:
     *      1 聲明正常隊列 和 正常交換機
     *      2 聲明死信隊列 和 死信交換機
     *      3 正常隊列 綁定 死信交換機
     *          設置兩個參數
     *            "x-dead-letter-exchange"      死信交換機名稱
     *            "x-dead-letter-routing-key"   發送給死信交換機的 routingkey
     */

//    public static final String EXCHANGE_NAME = "test_exchange_dlx";
//    public static final String QUEUE_NAME = "test_queue_dlx";
//
//    public static final String DLX_EXCHANGE_NAME = "exchange_dlx";
//    public static final String DLX_QUEUE_NAME = "queue_dlx";
//
//    // 1、聲明正常隊列  和 正常交換機
//    @Bean("test_exchange_dlx")
//    public Exchange Exchange() {
//        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();  //持久化:true
//    }
//
//
//    @Bean("test_queue_dlx")
//    public Queue Queue() {
//        // 3 正常隊列 綁定 死信交換機
//        final HashMap<String, Object> arguments
//                = new HashMap<>();
//        //设置死信交换机
//        arguments.put("x-dead-letter-exchange",DLX_EXCHANGE_NAME);
//        //设置死信RoutingKey
//        arguments.put("x-dead-letter-routing-key","dlx.haha");
//        //设置TTL设置10秒过期
//        arguments.put("x-message-ttl",10000);
//        //設置隊列長度限制
//        arguments.put("x-max-length",10);
//
//        return QueueBuilder.durable(QUEUE_NAME).withArguments(arguments).build();
//    }
//
//    @Bean
//    public Binding bindQueueExchange(@Qualifier("test_queue_dlx") Queue queue, @Qualifier("test_exchange_dlx") Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("test.dlx.#").noargs();//noargs 代表不需要指定參數
//    }
//
//    // 2 聲明死信隊列 和 死信交換機
//    @Bean("exchange_dlx")
//    public Exchange dlxExchange() {
//        return ExchangeBuilder.topicExchange(DLX_EXCHANGE_NAME).durable(true).build();  //持久化:true
//    }
//
//
//    @Bean("queue_dlx")
//    public Queue dlxQueue() {
//        return QueueBuilder.durable(DLX_QUEUE_NAME).build();
//    }
//
//    //3、Binding Queue and exchange
//    @Bean
//    public Binding bindDlxQueueAndDlxExchange(@Qualifier("queue_dlx") Queue queue, @Qualifier("exchange_dlx") Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("dlx.#").noargs();//noargs 代表不需要指定參數
//    }





    /**
     *
     * 延遲隊列 (TTL+DLX):
     *      1 聲明正常隊列 和 正常交換機
     *      2 聲明死信隊列 和 死信交換機
     *      3 正常隊列 綁定 死信交換機 過期時間為10秒
     *          設置兩個參數
     *            "x-dead-letter-exchange"      死信交換機名稱
     *            "x-dead-letter-routing-key"   發送給死信交換機的 routingkey
     */
    public static final String EXCHANGE_NAME = "order_exchange";
    public static final String QUEUE_NAME = "order_queue";

    public static final String DLX_EXCHANGE_NAME = "order_exchange_dlx";
    public static final String DLX_QUEUE_NAME = "order_queue_dlx";

    // 1、聲明正常隊列  和 正常交換機
    @Bean("order_exchange")
    public Exchange Exchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();  //持久化:true
    }


    @Bean("order_queue")
    public Queue Queue() {
        // 3 正常隊列 綁定 死信交換機
        final HashMap<String, Object> arguments
                = new HashMap<>();
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",DLX_EXCHANGE_NAME);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","dlx.order.cancel");
        //设置TTL设置10秒过期
        arguments.put("x-message-ttl",10000);

        return QueueBuilder.durable(QUEUE_NAME).withArguments(arguments).build();
    }

    @Bean
    public Binding bindQueueExchange(@Qualifier("order_queue") Queue queue, @Qualifier("order_exchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();//noargs 代表不需要指定參數
    }

    // 2 聲明死信隊列 和 死信交換機
    @Bean("order_exchange_dlx")
    public Exchange dlxExchange() {
        return ExchangeBuilder.topicExchange(DLX_EXCHANGE_NAME).durable(true).build();  //持久化:true
    }


    @Bean("order_queue_dlx")
    public Queue dlxQueue() {
        return QueueBuilder.durable(DLX_QUEUE_NAME).build();
    }

    //3、Binding Queue and exchange
    @Bean
    public Binding bindDlxQueueAndDlxExchange(@Qualifier("order_queue_dlx") Queue queue, @Qualifier("order_exchange_dlx") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("dlx.order.#").noargs();//noargs 代表不需要指定參數
    }


}
