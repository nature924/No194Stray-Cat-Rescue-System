package com.stray.cat.controller;

import com.github.pagehelper.PageInfo;
import com.stray.cat.dto.CatConst;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.Sentence;
import com.stray.cat.pojo.Share;
import com.stray.cat.pojo.User;
import com.stray.cat.service.SentenceService;
import com.stray.cat.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("share")
public class shareController {

    @Autowired
    ShareService shareService;
    @Autowired
    SentenceService sentenceService;

    @GetMapping("")
    public String share(Model model,HttpSession session,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "limit", defaultValue = "8") int limit){
        model.addAttribute("info",shareService.findPageAllShare(page,limit));
        model.addAttribute("search",null);
        model.addAttribute("indexMax",shareService.findMaxShare(4));
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "share";
    }

    @RequestMapping("search")
    public String indexSearch(String search,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "limit", defaultValue = "8") int limit,Model model){
        PageInfo<Share> info=shareService.findPageSearchShare(page,limit,search);
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        model.addAttribute("info",info);
        model.addAttribute("search",search);
        model.addAttribute("indexMax",shareService.findMaxShare(4));
        return "share";
    }

    @GetMapping("interceptor/writeShare")
    public String write(Model model, HttpSession session,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit){
        model.addAttribute("status",0);
        User user = (User) session.getAttribute(CatConst.USER_SESSION_KEY);
        model.addAttribute("info",shareService.findPageShare(page,limit));
        return "share_writing";
    }

    @GetMapping("interceptor/writeShareStatus")
    public String status(@RequestParam("status") int status, Model model,HttpSession session){
        model.addAttribute("status",status);
        if(status==0){
            return "redirect:/share/interceptor/writeShare";
        }else {
            return "share_writing";
        }
    }

    @PostMapping("interceptor/writeShare")
    @ResponseBody
    public JsonResult writeShare(Share share, HttpSession session){
        User user= (User) session.getAttribute(CatConst.USER_SESSION_KEY);
        share.setSharePhone(user.getUserPhone());
        share.setShareCreatetime(new Date());

        int i=shareService.addShare(share);
        if(i==1){
            return new JsonResult(true,"分享成功！");
        }else {
            return new JsonResult(false, "分享失败！");
        }
    }

    @GetMapping("interceptor/deleteShare")
    public String deletePost(@RequestParam("shareId") int shareId){
        shareService.deleteShare(shareId);
        return "redirect:/share/interceptor/writeShare";
    }

    @GetMapping("interceptor/updateShare")
    public String updateShare(@RequestParam("shareId") int shareId,Model model){
        Share share=shareService.queryShareById(shareId);
        System.out.println(share);
        model.addAttribute("share",share);
        model.addAttribute("status",1);
        return "share_writing";
    }

    @PostMapping("interceptor/updateShare")
    @ResponseBody
    public JsonResult getUpdateShare(Share share){
        share.setShareCreatetime(new Date());
        int i=shareService.updateShare(share);
        if(i==1){
            return new JsonResult(true,"修改成功！");
        }else {
            return new JsonResult(false, "修改失败！");
        }
    }


}
