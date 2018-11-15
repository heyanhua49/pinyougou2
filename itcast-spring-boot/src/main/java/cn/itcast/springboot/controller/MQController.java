package cn.itcast.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/mq")
@RestController
public class MQController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @GetMapping("/send")
    public String sendMapMsg(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", 123);
        map.put("name", "黑马");

        jmsMessagingTemplate.convertAndSend("spring.boot.queue", map);

        return "发送ActiveMQ消息到spring.boot.queue队列中成功。";
    }

    //发送短息
    @GetMapping("/sendSms")
    public String sendSmsMsg(){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "13600095062");
        map.put("signName", "黑马");
        map.put("templateCode", "SMS_125018593");
        map.put("templateParam", "{\"code\":654321}");

        jmsMessagingTemplate.convertAndSend("itcast_sms_queue", map);

        return "发送ActiveMQ消息到itcast_sms_queue队列中成功。";
    }
}
