package com.stray.cat.controller;

import com.github.pagehelper.PageInfo;
import com.stray.cat.dao.SentenceMapper;
import com.stray.cat.dto.CatConst;
import com.stray.cat.pojo.Notice;
import com.stray.cat.pojo.Sentence;
import com.stray.cat.service.NoticeService;
import com.stray.cat.service.SentenceService;
import com.stray.cat.service.additional.IndexService;
import com.stray.cat.vo.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class indexController {
    @Autowired
    IndexService indexService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    SentenceService sentenceService;
    @Autowired
    HttpSession session;


    @GetMapping
    public String index(Model model){
        PageInfo<IndexVo> info=indexService.findPageIndex();
        List<IndexVo> indexMax=indexService.findMaxCount();
        List<Notice> notices=noticeService.queryNotice();
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        model.addAttribute("notices",notices);
        model.addAttribute("search",null);
        model.addAttribute("indexMax",indexMax);
        model.addAttribute("info",info);
        return "index";
    }

    @RequestMapping("indexSearch")
    public String indexSearch(String search,Model model){
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
         PageInfo<IndexVo> info=indexService.findPageIndexSearch(search);
         model.addAttribute("info",info);
         model.addAttribute("search",search);
        List<Notice> notices=noticeService.queryNotice();
        model.addAttribute("notices",notices);
        List<IndexVo> indexMax=indexService.findMaxCount();
        model.addAttribute("indexMax",indexMax);
        return "index";
    }

    @GetMapping("out")
    public String index(){
        session.invalidate();
        return "redirect:";
    }


}
