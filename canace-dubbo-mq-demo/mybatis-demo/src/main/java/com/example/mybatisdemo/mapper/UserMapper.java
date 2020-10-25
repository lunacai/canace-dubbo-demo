package com.example.mybatisdemo.mapper;

import com.example.mybatisdemo.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(UserModel record);

    int insertSelectivetwo(UserModel record);

    UserModel selectByPrimaryKey(Long id);
}