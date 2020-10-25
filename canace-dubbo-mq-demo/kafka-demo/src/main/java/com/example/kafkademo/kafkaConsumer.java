package com.example.kafkademo;

import com.example.mybatisdemo.UserModel;
import com.example.mybatisdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class kafkaConsumer {
    @Autowired
    private UserMapper userMapper;

    @KafkaListener(topics = "${spring.kafka.topic}")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("topic={}, offset={}, message={}", record.topic(), record.offset(), record.value());
        UserModel user = new UserModel();
        user.setUsername("name_"+record.value().toString());
        user.setContent("content_"+record.topic());
        user.setDescription("description_"+record.offset());
        userMapper.insertSelective(user);
    }
}
