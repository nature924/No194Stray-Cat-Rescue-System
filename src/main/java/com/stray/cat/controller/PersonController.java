package com.stray.cat.controller;

import com.stray.cat.dto.JsonResult;
import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.User;
import com.stray.cat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("person/interceptor")
public class PersonController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @GetMapping
    private String index(Model model){
        User user= (User) session.getAttribute("user_session");
        User user1=userService.queryByPhone(user.getUserPhone());
        model.addAttribute("user",user1);
        return "person_writing";
    }

    @PostMapping("updateUser")
    @ResponseBody
    public JsonResult getUpdatePost(User user){
        User user1= (User) session.getAttribute("user_session");
        user.setUserPhone(user1.getUserPhone());
        int i=userService.updateUser(user);
        System.out.println(user);
        if(i==1){
            return new JsonResult(true,"修改成功！");
        }else {
            return new JsonResult(false, "修改失败！");
        }
    }

}
