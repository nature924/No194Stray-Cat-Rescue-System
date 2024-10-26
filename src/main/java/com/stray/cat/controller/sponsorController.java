package com.stray.cat.controller;

import com.stray.cat.dto.CatConst;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.Sentence;
import com.stray.cat.pojo.Sponsor;
import com.stray.cat.pojo.User;
import com.stray.cat.service.PostService;
import com.stray.cat.service.SentenceService;
import com.stray.cat.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("sponsor")
public class sponsorController {
    @Autowired
    SponsorService sponsorService;
    @Autowired
    SentenceService sentenceService;

    @GetMapping("")
    public String sponsor(Model model,HttpSession session,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "limit", defaultValue = "8") int limit){
        User user = (User) session.getAttribute(CatConst.USER_SESSION_KEY);
        model.addAttribute("info",sponsorService.findPagePostSponsorse(page,limit));
        model.addAttribute("indexMax",sponsorService.findindexMaxSponsorse(4));
        model.addAttribute("search",null);
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "sponsor";
    }


    @GetMapping("interceptor/writeSponsor")
    public String write(Model model,HttpSession session,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit){
        model.addAttribute("status",0);
        User user = (User) session.getAttribute(CatConst.USER_SESSION_KEY);
        model.addAttribute("info",sponsorService.findPageSponsorTowrite(page,limit));
        return "sponsor_writing";
    }

    @GetMapping("interceptor/writeSponsorStatus")
    public String status(@RequestParam("status") int status, Model model,HttpSession session){
        model.addAttribute("status",status);
        if(status==0){
            return "redirect:/sponsor/interceptor/writeSponsor";
        }else {
            return "sponsor_writing";
        }
    }
    @PostMapping("interceptor/writeSponsor")
    @ResponseBody
    public JsonResult writePost(Sponsor sponsor, HttpSession session){
        User user= (User) session.getAttribute(CatConst.USER_SESSION_KEY);
        sponsor.setSponsorPhone(user.getUserPhone());
        sponsor.setSponsorCreatetime(new Date());
        sponsor.setSponsorStatus(0);

        int i=sponsorService.addSponsor(sponsor);
        if(i==1){
            return new JsonResult(true,"发布成功！");
        }else {
            return new JsonResult(false, "发布失败！");
        }
    }

    @GetMapping("interceptor/deleteSponsor")
    public String deletePost(@RequestParam("sponsorId") int sponsorId){
        sponsorService.deletesponsor(sponsorId);
        return "redirect:/sponsor/interceptor/writeSponsor";
    }

    @GetMapping("interceptor/updateSponsor")
    public String updatePost(@RequestParam("sponsorId") int sponsorId,Model model){
        Sponsor sponsor=sponsorService.querySponsorById(sponsorId);
        model.addAttribute("sponsor",sponsor);
        model.addAttribute("status",1);
        return "sponsor_writing";
    }

    @PostMapping("interceptor/updatePost")
    @ResponseBody
    public JsonResult getUpdatePost(Sponsor sponsor){

        sponsor.setSponsorCreatetime(new Date());
        sponsor.setSponsorStatus(0);
        int i=sponsorService.updateSponsor(sponsor);
        if(i==1){
            return new JsonResult(true,"修改成功！");
        }else {
            return new JsonResult(false, "修改失败！");
        }
    }
    @RequestMapping("Search")
    public String Search(Model model,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "limit", defaultValue = "8") int limit,
                       @RequestParam("search") String search ){
        model.addAttribute("search",search);
        search="%"+search+"%";
        model.addAttribute("info",sponsorService.findPagePostSponsorseByname(page,limit,search));
        model.addAttribute("indexMax",sponsorService.findindexMaxSponsorse(4));
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "sponsor";

    }







}
