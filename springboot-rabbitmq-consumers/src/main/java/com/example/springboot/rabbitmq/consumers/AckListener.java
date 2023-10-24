package com.example.springboot.rabbitmq.consumers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class AckListener implements ChannelAwareMessageListener {
//    @RabbitListener(queues = "test_queue_confirm")
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{
            //1、接收轉換消息
            System.out.println(new String(message.getBody()));

            //2、處理業務邏輯
            System.out.println("處理業務邏輯....");
//            int i = 3/0; //模擬出現錯誤

            //3、手動簽收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e ){
            //4、拒絕簽收
            /*
            第三個參數: requeue重回隊列，如設置為true，則消息重回到queue，broker會重新發送消息給消費端
             */
            channel.basicNack(deliveryTag,true,true);
//            channel.basicReject(deliveryTag,true);
        }
    }
//    @RabbitListener(queues = "boot_queue")
//    public void ListenerQueue(Message message) {
//        System.out.println(message);
//        System.out.println(new String(message.getBody()));
//    }

//        @RabbitListener(queues = "test_queue_confirm")
//    public void ListenerQueue(Message message) {
//        System.out.println(new String(message.getBody()));
//    }


}
