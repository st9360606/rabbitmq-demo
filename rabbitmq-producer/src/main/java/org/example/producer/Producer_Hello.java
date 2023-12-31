package org.example.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 發送消息
 */
public class Producer_Hello {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1. 創建連接工廠
        ConnectionFactory factory = new ConnectionFactory();

        //2. 設置參數
        factory.setHost("192.168.56.101");  //默認值localhost
        factory.setPort(5672);   //默認值:5672
        factory.setVirtualHost("/rabbitmq"); //虛擬機  默認值:/
        factory.setUsername("kurt");
        factory.setPassword("1234");

        //3. 創建Connection
        Connection connection = factory.newConnection();

        //4. 創建Channel
        Channel channel = connection.createChannel();

        //5. 创建队列Queue
        /*
        queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
        参数：
            1. queue：队列名称
            2. durable:是否持久化，当mq重启之后，还在
            3. exclusive：
                * 是否独占。只能有一个消费者监听这队列
                * 当Connection关闭时，是否删除队列
                *
            4. autoDelete:是否自动删除。当没有Consumer时，自动删除掉
            5. arguments：参数。

         */
        //如果没有一个名字叫hello_world的队列，则会创建该队列，如果有则不会创建
        channel.queueDeclare("hello_world",true,false,false,null);
        /*
        basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        参数：
            1. exchange：交换机名称。简单模式下交换机会使用默认的 ""
            2. routingKey：路由名称
            3. props：配置信息
            4. body：发送消息数据

         */

        String body = "hello rabbitmq~~~";

        //6. 发送消息
        channel.basicPublish("","hello_world",null,body.getBytes());


        //7.释放资源
        channel.close();
        connection.close();

    }
}
