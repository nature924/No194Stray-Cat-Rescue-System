package com.stray.cat.controller.admin;

import com.stray.cat.dao.PostMapper;
import com.stray.cat.dao.ShareMapper;
import com.stray.cat.dao.SponsorMapper;
import com.stray.cat.dto.CatConst;
import com.stray.cat.pojo.Sponsor;
import com.stray.cat.pojo.User;
import com.stray.cat.service.PostService;
import com.stray.cat.service.ShareService;
import com.stray.cat.service.SponsorService;
import com.stray.cat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin/user")
public class adminUserController {
    @Autowired
    UserService userService;
    @Autowired
    PostMapper postMapper;
    @Autowired
    ShareMapper shareMapper;
    @Autowired
    SponsorMapper sponsorMapper;

    @GetMapping("")
    public String write(Model model, HttpSession session,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "12") int limit){
        model.addAttribute("info",userService.queryUserAll(page,limit));
        return "admin/admin_user";
    }

    @GetMapping("delete")
    public String deleteUser(@RequestParam("userId") int userId){
        User user=userService.queryUserById(userId);
        if(user.getUserPhone().equals("1111")){
            return "redirect:/admin/user";
        }
        postMapper.deletePostByPhone(user.getUserPhone());
        shareMapper.deleteShareByPhone(user.getUserPhone());
        sponsorMapper.deleteSponsorByPhone(user.getUserPhone());
        userService.deleteUser(userId);
        return "redirect:/admin/user";
    }


}
