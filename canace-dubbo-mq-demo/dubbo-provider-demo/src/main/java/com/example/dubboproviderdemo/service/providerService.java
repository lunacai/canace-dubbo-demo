package com.example.dubboproviderdemo.service;

import com.example.mybatisdemo.UserModel;

public interface providerService {
    String sayHello(String name);

    String tankDBAddUser(UserModel user);

    String toKafka(String name);
}
