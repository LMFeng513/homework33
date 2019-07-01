package com.qf.controller;

  /*
    @author: LMFeng
    @date: 2019-06-28 19:50
    @desc:
  */
import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Message;
import com.qf.entity.User;
import com.qf.service.IMessageService;
import com.qf.service.IUserService;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/*  mail:
    protocol: smtp
    host: smtp.sina.com
    username: lmf513
    password: lmf123456
*/
@Controller
@RequestMapping("/user")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class UserController {
    @Reference
    private IUserService userService;
    @Reference
    private IMessageService messageService;

    @RequestMapping("/tologin")
    public String toLogin(){

        return "login";
    }

    @RequestMapping("/login")
    public Object login(@RequestParam String name, @RequestParam String password){
        Map<String,Object> map = new HashMap<>();
        map.put("username",name);
        map.put("password",password);
        List<User> users = userService.login(map);
        if(users!=null){
            return  "success";
        }
        return  "login";
    }

    @RequestMapping("/toregister")
    public String toRegister(){

        return "register";
    }


    @ResponseBody
    @RequestMapping("/registermessage")
    public Object sendRegisterMessage(@RequestBody User user, HttpServletRequest request){
        System.out.println(user);
        Message message = new Message();
        message.setSendurl("lmfeng513@sina.com");
        message.setTitle("发送验证码");
        message.setReceiveurl(user.getEmail());
        Random random = new Random();
        Integer code= 1000+ 9*random.nextInt(1000);
        message.setCode(code);
        request.getSession().setAttribute("code",message.getCode());
        messageService.sendRegisterMessage(message);
        return true;
    }
    /*注册用户*/
    @RequestMapping("/register")
    public String register(User user, Message message, HttpServletRequest request, Model model){
        System.out.println("11111");
        System.out.println(user+"==="+message);
        if(request.getSession().getAttribute("code").equals(message.getCode())){
            userService.register(user);
            model.addAttribute("msg","");
            return "login";
        }
        model.addAttribute("msg","注册失败,请重新注册");
        return "register";
    }



    /*@RequestMapping("/sendMail")
    @ResponseBody
    public String sendMail(String email) {

        //创建一封邮件
        MimeMessage mimeMessage =javamailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);

        //设置标题
        try {
            messageHelper.setSubject("注册认证");

            //设置发送者
            messageHelper.setFrom("lmfeng513@sina.com");

            //设置接收者
            messageHelper.setTo(email);

            messageHelper.setText("请点击<a href='localhost:8080/xgpassword'>这里</a>找回密码~", true);


            javamailSender.send(mimeMessage);
            return "succ";
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return "error";
    }
*/

}
