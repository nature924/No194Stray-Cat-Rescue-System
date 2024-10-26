package com.stray.cat.controller.admin;


import com.stray.cat.dto.CatConst;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.pojo.Notice;
import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.User;
import com.stray.cat.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/notice")
public class adminNoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping
    public String index(Model model,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "limit", defaultValue = "12") int limit){
        model.addAttribute("info",noticeService.findPageNotice(page,limit));
        return "admin/admin_notice";
    }

    @GetMapping("deleteNotice")
    public String deleteNotice(int noticeId){
        noticeService.deleteNotice(noticeId);
        return "redirect:/admin/notice";
    }

    @PostMapping("addNotice")
    @ResponseBody
    public JsonResult writePost(String noticeContent){
        Notice notice=new Notice();
        notice.setNoticeContent(noticeContent);
        notice.setNoticeCreatetime(new Date());
        int i=noticeService.addNotice(notice);
        if(i==1){
            return new JsonResult(true,"添加成功！");
        }else {
            return new JsonResult(false, "添加失败！");
        }
    }

}
