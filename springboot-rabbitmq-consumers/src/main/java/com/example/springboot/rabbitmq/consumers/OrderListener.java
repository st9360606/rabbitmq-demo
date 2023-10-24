package com.example.springboot.rabbitmq.consumers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener implements ChannelAwareMessageListener {
    //定義監聽器，延遲隊列效果實現: 監聽死信隊列，才有延遲的效果!!!
    @RabbitListener(queues = "order_queue_dlx")
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{
            //1、接收轉換消息
            System.out.println(new String(message.getBody()));

            //2、處理業務邏輯
            System.out.println("處理業務邏輯....");
            System.out.println("根據訂單id查詢其狀態...");
            System.out.println("判斷狀態是否支付成功...");
            System.out.println("取消訂單，退回庫存...");

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
