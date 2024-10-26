package com.stray.cat.controller.admin;

import com.stray.cat.enums.SponsorStatus;
import com.stray.cat.pojo.Sponsor;
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
@RequestMapping("admin/sponsor")
public class adminSponsorController {

    @Autowired
    SponsorService sponsorService;

    @GetMapping("")
    public String write(Model model, HttpSession session,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit){
        model.addAttribute("info",sponsorService.findPagePostSponsorVo(page,limit));
        return "admin/admin_sponsor";
    }


    @GetMapping("adopt")
    public String adopt(@RequestParam("sponsorId") int sponsorId){
        sponsorService.updateSponsorStatus(SponsorStatus.Push.getStatus(),sponsorId);
        return "redirect:/admin/sponsor";
    }

    @GetMapping("repulse")
    public String repulse(@RequestParam("sponsorId") int sponsorId){
        sponsorService.updateSponsorStatus(SponsorStatus.Repulse.getStatus(),sponsorId);
        return "redirect:/admin/sponsor";
    }

    @GetMapping("look")
    public String look(@RequestParam("sponsorId") int sponsorId,Model model){
        Sponsor sponsor=sponsorService.querySponsorById(sponsorId);
        model.addAttribute("sponsor",sponsor);
        return "admin/admin_sponsor_look";
    }



}
