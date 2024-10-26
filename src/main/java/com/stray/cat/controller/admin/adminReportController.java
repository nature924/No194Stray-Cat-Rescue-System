package com.stray.cat.controller.admin;


import com.stray.cat.notification.EmailService;
import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.Share;
import com.stray.cat.pojo.Sponsor;
import com.stray.cat.pojo.User;
import com.stray.cat.service.PostService;
import com.stray.cat.service.ShareService;
import com.stray.cat.service.SponsorService;
import com.stray.cat.service.UserService;
import com.stray.cat.service.additional.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("admin/report")
public class adminReportController {

    @Autowired
    ReportService reportService;
    @Autowired
    HttpSession session;
    @Autowired
    ShareService shareService;
    @Autowired
    SponsorService sponsorService;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;


    @GetMapping
    public String report(Model model,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "limit", defaultValue = "12") int limit){
        model.addAttribute("info",reportService.findPageReport(page,limit));
        return "admin/admin_report";
    }

    @GetMapping("deleteReport")
    public String deleteReport(int id,String status,int reportId) throws UnsupportedEncodingException, MessagingException {
        System.out.println(id);
        if(status.equals("启示")){
            Post post=postService.queryPostById(id);
            User user=userService.queryByPhone(post.getPostPhone());
            emailService.sendEmail(user.getUserQq()+"@qq.com",post.getPostTitle());
            postService.deletePost(id);

        }if(status.equals("分享")){
            Share share=shareService.queryShareById(id);
            System.out.println(share);
            User user=userService.queryByPhone(share.getSharePhone());
            emailService.sendEmail(user.getUserQq()+"@qq.com",share.getShareTitle());
            shareService.deleteShare(id);

        }if(status.equals("赞助")){
            Sponsor sponsor=sponsorService.querySponsorById(id);
            User user=userService.queryByPhone(sponsor.getSponsorPhone());
            emailService.sendEmail(user.getUserQq()+"@qq.com",sponsor.getSponsorTitle());
            sponsorService.deletesponsor(id);

        }
        reportService.deleteReport(reportId);
        return "redirect:/admin/report";
    }

    @GetMapping("deleteReport2")
    public String deleteReport2(int id){
        reportService.deleteReport(id);
        return "redirect:/admin/report";
    }

}
