package com.example.dubboproviderdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan("com.example")
@MapperScan("com.example.mybatisdemo")
@ImportResource("classpath:dubbo-provider.xml")
public class DubboProviderDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderDemoApplication.class, args);
	}

}
