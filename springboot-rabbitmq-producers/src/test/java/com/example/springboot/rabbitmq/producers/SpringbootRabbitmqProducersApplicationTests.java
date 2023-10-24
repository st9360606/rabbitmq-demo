package com.example.springboot.rabbitmq.producers;

import com.example.springboot.rabbitmq.producers.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootRabbitmqProducersApplicationTests {

    //1.注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;


    //RabbitMQ 高級特性  消息的可靠性，有下面兩種模式

    /**
     * 開啟確認模式 : publisher-confirms: true
     * 步驟:
     * 1 設置publisherConfirms: true
     * 2 在rabbitTemplate定義ConfirmCallback回調函數
     */
    @Test
    public void testConfirm() {

        //2 定義回調
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("Confirm方法被執行了....");

                if (ack) {
                    //接收成功
                    System.out.println("接收消息成功" + cause);
                } else {
                    //接收失敗
                    System.out.println("接收消息失敗" + cause);
                }
            }
        });

        //3 發送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm.....");

    }

    /**
     * 回退模式 : 當消息發送給Exchange後，Exchange路由到Queue失敗是才會執行 ReturnCallBack
     * 步驟:
     * 1 開啟回退模式 publisher-returns: true
     * 2 設置ReturnCallBack
     * 3 設置Exchange處理消息的模式
     *      1 如果消息沒有路由到Queue，就丟棄消息(默認情況下)
     *      2 如果消息沒有路由到Queue，返回給消息發送方ReturnCallBack
     */
    @Test
    public void testReturn() {

        //設置交換機處理失敗消息的模式
        rabbitTemplate.setMandatory(true);

        //2 設置ReturnCallBack
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {

            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                //replyCode 錯誤碼
                //replyText 錯誤信息
                System.out.println("return 執行了.....");

                System.out.println(message);
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
            }
        });

        //3 發送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirmXXXX", "message confirm.....");

    }


    @Test
    public void testSend(){

        for (int i = 0; i < 10; i++) {
            //發送消息
            rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm.....");
        }

    }

    /**
     *
     * TTL:過期時間
     * 1 隊列統一過期
     *
     * 2 消息單獨過期
     *
     * 如果同時設置 消息過期時間 跟 隊列過期時間 ， 他以時間短的為準
     *
     * 隊列過期後，會將隊列所有消息全部移除
     *
     * 消息過期後，只有消息在隊列頂端，才會判斷其是否過期(移除掉)
     *
     */
    @Test
    public void testTtl(){
        //統一過期
//        for (int i = 0; i < 10; i++) {
//            //發送消息
//            rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hello", "message ttl.....");
//        }

        //消息單獨過期
        rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hello", "message ttl.....",
                new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //設置message的信息
                message.getMessageProperties().setExpiration("5000"); //消息的過期時間
                //返回該消息
                return message;
            }
        });
    }

    /**
     * 發送測試死信消息
     * 1. 過期時間
     * 2. 長度限制
     * 3. 消息拒收
     */
    @Test
    public void testDlx(){
        //1. 測試過期時間，死信隊列
//        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","死信隊列.....");

        //2. 測試長度限制，死信隊列
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","死信隊列.....");
//        }

        //3. 測試消息拒收，死信隊列
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","死信隊列.....");


    }

    @Test
    public void testDelay() throws InterruptedException {
        //1、發送訂單消息，將來是在訂單系統中，下單成功後，發送消息
        rabbitTemplate.convertAndSend("order_exchange","order.msg","訂單信息: id=1 ,time=2023/10/5");

        for (int i = 10; i > 0; i--) {
            System.out.println(i+"....");
            Thread.sleep(1000);
        }
    }
}
