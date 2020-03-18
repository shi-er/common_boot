package com.shier.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;

/**
 * App 消费者-队列模式
 */
public class AppConsumer {
    /**
     * 指定ActiveMQ服务的地址
     */
    private static final String URL = "tcp://127.0.0.1:61616";
    /**
     * 指定队列的名称
     */
    private static final String QUEUE_NAME = "queue-test";

    @Test
    public void consumer() throws JMSException {

        // 1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        // 2.创建Connection
        Connection connection = connectionFactory.createConnection();
        // 3.启动连接
        connection.start();
        // 4.创建会话（第一个参数：是否在事务中处理）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建一个目标
        Destination destination = session.createQueue(QUEUE_NAME);
        // 6.创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);
        // 7.创建一个监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息：" + textMessage.getText());
                } catch (JMSException e) {
                    System.out.println("接收消息异常：");
                    e.printStackTrace();
                }
            }
        });
        // 8.关闭连接
        //connection.close();
    }

}