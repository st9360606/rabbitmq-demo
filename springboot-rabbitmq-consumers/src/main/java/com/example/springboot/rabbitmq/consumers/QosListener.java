package com.example.springboot.rabbitmq.consumers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 *
 * Cousmer 限流機制
 *      1. 確保ack機制為手動確認
 *      2. 配置屬性 perfetch = 1，表示消費端每次從MQ拉去一條消息來消費，直到手動確認消費完畢後，才會繼續拉下一條消息。
 */
@Component
public class QosListener implements ChannelAwareMessageListener {
//    @RabbitListener(queues = "test_queue_confirm")
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        Thread.sleep(100000);

        //1 獲取消息
        System.out.println(new String(message.getBody()));

        //2 處理業務邏輯

        //3 簽收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    }
}




