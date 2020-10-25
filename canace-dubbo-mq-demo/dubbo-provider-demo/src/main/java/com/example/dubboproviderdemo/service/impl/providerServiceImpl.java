package com.example.dubboproviderdemo.service.impl;

import com.example.dubboproviderdemo.service.kafkaProducer;
import com.example.dubboproviderdemo.service.providerService;
import com.example.mybatisdemo.UserModel;
import com.example.mybatisdemo.mapper.UserMapper;
import com.pamirs.pradar.maxplanck.module.agent.shared.service.PressureMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class providerServiceImpl implements providerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private kafkaProducer kafkaproducer;

    @Value("${kafka_topic}")
    private String topic;

    @Value(value = "${kafka_ip}")
    private String KAFKA_IP;

    public String sayHello(String name) {
        UserModel user=new UserModel();
        user.setUsername(name);
        user.setDescription("dubbo-provider"+name);
        user.setContent("dubbo-provider-content"+name);
        userMapper.insertSelectivetwo(user);
        System.out.println("consumer>>>>>>"+name);
        return "Hello " + name;
    }

    @Override
    public String tankDBAddUser(UserModel user) {
        System.out.println("user>>>>>>>>" + user.toString());
        userMapper.insertSelectivetwo(user);
        return "add User to tankDB Successful!!";
    }

    @Override
    public String toKafka(String name) {
        System.out.println("kafka>>>>>>>>" + name);
        System.out.println("Pradar>>>>>>>>>>>>>>>" + PressureMeasurement.ispressureMeaurement());
        kafkaproducer.sendAsyn(KAFKA_IP, topic, name.toString());
        return "add User to kafka Successful!!";
    }

}
