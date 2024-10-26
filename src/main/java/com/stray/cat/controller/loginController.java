package com.stray.cat.controller;

import com.stray.cat.dao.UserMapper;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.dto.LoginResult;
import com.stray.cat.dto.CatConst;
import com.stray.cat.notification.DuanxinService;
import com.stray.cat.pojo.User;
import com.stray.cat.service.UserService;
import com.stray.cat.util.MD5Util;
import com.stray.cat.util.TitleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("")
public class loginController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"/login"})
    public String login(){
        return "login";
    }

    @GetMapping("reBack")
    public String reBack(HttpSession session){
        String url= (String) session.getAttribute("user_old_url");
        if(url==null){
            url="/";
        }
        return "redirect:"+url;
    }

    @GetMapping(value = {"login1"})
    public String login1(@RequestParam(value ="userUrl") String userUrl,HttpSession session){
        if(userUrl!=null){
            session.setAttribute(CatConst.USER_SESSION_URL,userUrl);
        }
        return "login";
    }

    @PostMapping("login/getLogin")
    @ResponseBody
    public LoginResult getLogin(@RequestParam(value ="phone") String phone,
                                @RequestParam(value ="password") String password, HttpSession session) {
        User user = userService.queryByPhone(phone);
        if(user!=null){
            if(user.getUserPassword().equals(MD5Util.getMD5(password))){
                session.setAttribute(CatConst.USER_SESSION_KEY, user);
                if(user.getUserPhone().equals("1111")){
                    session.setAttribute(CatConst.USER_SESSION_administrators, user.getUserPhone());
                }
                return new LoginResult(true, "登录成功");
            }else {
                return new LoginResult(false, "密码错误");
            }
        }else {
            return new LoginResult(false, "用户名错误");
        }
    }

    @PostMapping("/login/register")
    @ResponseBody
    public JsonResult register(User user,@RequestParam(value ="userCode") String userCode,HttpSession session){
        String code=session.getAttribute(CatConst.USER_SESSION_CODE).toString();
        if(code.equals(userCode)){
            if(userService.queryByPhone(user.getUserPhone())!=null){
                return new JsonResult(false, "电话号码已经注册！");
            }else {
                user.setUserPassword(MD5Util.getMD5(user.getUserPassword()));
                user.setUserCreatetime(new Date());
                user.setUserUrl(TitleUtil.getUrl());
                int i=userService.addUser(user);
                if(i==1){
                    session.removeAttribute(CatConst.USER_SESSION_CODE);
                    return new JsonResult(true, "注册成功！");
                }else {
                    return new JsonResult(false, "注册失败！");
                }
            }
        }else {
            return new JsonResult(false, "验证码错误！");
        }
    }

    @PostMapping("login/updatePassword")
    @ResponseBody
    public JsonResult updatePassword(@RequestParam(value ="userPhone") String userPhone,
                                     @RequestParam(value ="userCode") String userCode,
                                     @RequestParam(value ="userPassword") String userPassword,HttpSession session){
        String code=session.getAttribute(CatConst.USER_SESSION_CODE).toString();
        if(code.equals(userCode)){
            int i=userService.updateUserPhone(MD5Util.getMD5(userPassword),userPhone);
            if(i==1){
                session.removeAttribute(CatConst.USER_SESSION_CODE);
                return new JsonResult(true, "密码更新成功！");
            }else {
                return new JsonResult(false, "密码更新失败！");
            }
        }else {
            return new JsonResult(false, "验证码错误！");
        }
    }

    @PostMapping("login/getCode")
    @ResponseBody
    public JsonResult getCode(@RequestParam(value ="userPhone") String userPhone,HttpSession session){
        DuanxinService duanxinService=new DuanxinService();
//       int code=duanxinService.duanXin(userPhone);
       session.setAttribute(CatConst.USER_SESSION_CODE,"1234");
       return new JsonResult(true,"发送成功");
    }



//    有时，一些网站的部分操作需要登录才能访问。如果跳转到登录界面登录成功后，怎样才能返回到登录之前的界面呢？
//    很简单，我们在代码部分中添加一部分很少的代码即可。在从一个页面跳转到登录界面之前的代码，我们用session保
//    存当前界面的url信息，在跳转到登录界面，登录成功后的代码中，判读是否有这个session信息，如果有，则跳转到
//    session所存的url，记住跳转前清空这个session，否则在未关闭浏览器重新登录时可能又会跳到session中所存储url的界面。

}
