package com.example.springboot_websocket.controller;

import com.example.springboot_websocket.client.ClientServer;
import com.example.springboot_websocket.pojo.User;
import com.example.springboot_websocket.utils.SendMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.rmi.MarshalException;

/**
 * @author zhuzhaoman
 * @date 2020/6/14 0014 22:15
 * @description 描述
 */
@Controller
public class GreetingController {

    @Autowired
    public GreetingController(SimpMessagingTemplate messagingTemplate) {
        SendMessage.messagingTemplate = messagingTemplate;
    }

    @Resource
    private ClientServer clientServer;

    /**
     * ＠MessageMapping("/hello")注解的方法将用来接收"/app/hello"路径发送来的消息
     * 在注解方法中对消息进行处理后，再将消息转发到@SendTo定义的路径上，而@SendTo路径
     * 是一个前缀为"/topic"的路径，因此该消息将被交给消息代理broker，再由broker进行广播
     * @param message
     * @return
     */
//    @MessageMapping("/hello") // 接收/app/hello路径发送来的消息
//    @SendTo("/topic/greetings") // 将接收到的信息转发到/topic/greetings
//    public Message greeting(Message message) {
//        return message;
//    }

//    @MessageMapping("/hello")
//    public void greeting(Message message) {
//        messagingTemplate.convertAndSend("/topic/greetings", message);
//    }

    @MessageMapping("/login")
    public void greeting(User user) throws InterruptedException {
        System.out.println("开始登陆....");
        System.out.println("用户账号：" + user.getUsername());
        System.out.println("用户密码：" + user.getPassword());
        String content = new Gson().toJson(user);
        System.out.println(content);
        clientServer.sendMessage(content);
    }

//    @MessageMapping("/chat")
//    public void chat(Chat chat) {
//        System.out.println(chat.getTo());
//        /**
//         * 这里destinationPrefix默认值为/user，也就是说消息最终发送路径是/user/用户/queue/chat
//         * 这是因为SimpMessagingTemplate类中自动添加前缀
//         */
//        messagingTemplate.convertAndSendToUser(chat.getTo()+"","/queue/chat", chat);
//    }

//    @SubscribeMapping("/greetings")
//    public Message sub() {
//        return new Message("消息","谢谢你订阅了我！");
//    }
}
