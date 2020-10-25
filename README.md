# canace-dubbo-demo
dubbo,rocketmq,mybatis,kafka

调用逻辑：
1、canace-test-demo中的DemoControllerTwo开放http调用接口，调用rocketMQ。
2、rocketMQ消费端获取消息，插入数据库
3、进行参数判断，调用dubbo provider的接口
4、dubbo provider中插入数据和调用kafka
5、kafka接收端识别到后，调用数据库
