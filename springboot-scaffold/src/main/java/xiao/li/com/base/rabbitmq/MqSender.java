package xiao.li.com.base.rabbitmq;//package com.changhong.mcc.base.rabbitmq;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//
////@Component
//public class MqSender {
//
//
//    private static RabbitTemplate rabbitTemplate;
//    @Autowired
//    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
//        MqSender.rabbitTemplate = rabbitTemplate;
//    }
//
//    /**
//    * Description direct 消息<br>
//    *
//    * @param queue <br>
//    * @param content <br>
//    * @return void <br>
//    * @throws Exception <br>
//    * @author taotao <br>
//    * @createDate 2020-03-03 10:15 <br>
//    **/
//    public static void sendMessage(String queue, String content){
//        rabbitTemplate.convertAndSend(queue, content);
//    }
//
//    /**
//    * Description topic 消息<br>
//    *
//    * @param exchange <br>
//    * @param routingKey <br>
//    * @param content <br>
//    * @return void <br>
//    * @throws Exception <br>
//    * @author taotao <br>
//    * @createDate 2020-03-03 10:15 <br>
//    **/
//    public static void sendTopicMessage(String exchange , String routingKey, String content){
//        rabbitTemplate.convertAndSend(exchange,routingKey, content);
//    }
//}
