package com.example.canacetestdemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoControllerTwo {
    private DefaultMQProducer producer;
    private mqProducer mqproducer=new mqProducer();

    @Value("${sbootitframe.rocketmq.namesrvAddr}")
    private String address;//="192.168.1.117:9876";
    @Value("${sbootitframe.rocketmq.topic1}")
    private String topic;//="user-topic"


    @GetMapping("/sendMQToDB/{username}")
    public String sendMQToDB(@PathVariable String username){
        Message msg = new Message(topic, "white", username.getBytes());
        try {
            producer=mqproducer.getProducer();
            SendResult sendResult = producer.send(msg);
            System.out.println("消息id:" + sendResult.getMsgId() + "," + "发送状态:" + sendResult.getSendStatus());
            return sendResult.getMsgId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
