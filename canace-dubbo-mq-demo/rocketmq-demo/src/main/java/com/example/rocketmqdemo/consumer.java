package com.example.rocketmqdemo;

import com.example.dubboproviderdemo.service.providerService;
import com.example.mybatisdemo.UserModel;
import com.example.mybatisdemo.mapper.UserMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
public class consumer {
    /**
     * 消费者实体对象
     */
    private DefaultMQPushConsumer consumerMq;
    /**
     * 消费者组
     */
    public static final String CONSUMER_GROUP = "test_consumer";
    private String address = "192.168.1.117:9876";
    private String topic = "canace-topic";
    private List<String> userlist = new ArrayList<String>();
    private List<String> userlists = new ArrayList<String>();
    private List<String> userlisttwo = new ArrayList<String>();
    private List<String> userlistsan = new ArrayList<String>();

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private providerService providerService;

    /**
     * 通过构造函数 实例化对象
     */
    public consumer() throws MQClientException {
        consumerMq = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumerMq.setNamesrvAddr(address);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumerMq.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumerMq.subscribe(topic, "white");
        consumerMq.setVipChannelEnabled(false);
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumerMq.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            try {
                for (Message msg : msgs) {
                    //消费者获取消息 这里只输出 不做后面逻辑处理
                    String body = new String(msg.getBody(), "utf-8");
                    System.out.println("Consumer-获取消息-主题topic为={}, 消费消息为=" + msg.getTopic() + body);
                    insterDB(userlist, body);
                    if (body.equals("111")) {
                        System.out.println("Consumer-111>>>>>>>" + body);
                        testDubbo(userlists, body);
                    }else if(body.contains("222")){
                        System.out.println("Consumer-222>>>>>>>" +  body);
                        testDubboAdd(userlisttwo,body);
                    }else if(body.contains("33")){
                        System.out.println("Consumer-333>>>>>>>" +  body);
                        testDubbokafka(userlistsan,body);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumerMq.start();
        System.out.println("消费者 启动成功=======");
    }


    public void insterDB(List<String> userlist, String body) {
        if (!userlist.contains(body)) {
            UserModel user = new UserModel();
            user.setUsername("test-" + body);
            user.setDescription("description-" + body);
            user.setContent("content-" + body);
            System.out.println("user>>>>>>>>" + user.toString());
            userMapper.insertSelectivetwo(user);
            userlist.add(body);
        }

    }

    public void testDubbo(List<String> userlist, String body) {
        if (!userlist.contains(body)) {
            System.out.println("Provider>>>>>>>" + providerService.sayHello(body));
        }
    }

    public void testDubboAdd(List<String> userlist, String body) {
        if (!userlist.contains(body)) {
            UserModel user=new UserModel();
            user.setUsername("dubbo"+body);
            user.setContent("content"+body);
            user.setDescription("description"+body);
            System.out.println("Provider>>>>>>>" + providerService.tankDBAddUser(user));
        }
    }
    public void testDubbokafka(List<String> userlist, String body) {
        if (!userlist.contains(body)) {
            System.out.println("Provider>>>>>>>" + providerService.toKafka(body));
        }
    }
}