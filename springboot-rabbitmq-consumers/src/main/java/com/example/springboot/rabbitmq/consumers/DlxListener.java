package com.example.springboot.rabbitmq.consumers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class DlxListener implements ChannelAwareMessageListener {
    //定義監聽器，監聽正常隊列
//    @RabbitListener(queues = "test_queue_dlx")
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{
            //1、接收轉換消息
            System.out.println(new String(message.getBody()));

            //2、處理業務邏輯
            System.out.println("處理業務邏輯....");
            int i = 3/0; //模擬出現錯誤

            //3、手動簽收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e ){
            //4、拒絕簽收，不重回隊列這樣才能路由到死信隊列裡面
            /*
            第三個參數: requeue重回隊列，如設置為true，則消息重回到queue，broker會重新發送消息給消費端
             */
            System.out.println("出現異常，拒絕接受");
            channel.basicNack(deliveryTag,true,false);
        }
    }



}
