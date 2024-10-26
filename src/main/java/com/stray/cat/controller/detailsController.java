package com.stray.cat.controller;

import com.stray.cat.dao.CommentMapper;
import com.stray.cat.dto.CatConst;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.enums.IndexFrom;
import com.stray.cat.pojo.*;
import com.stray.cat.service.*;
import com.stray.cat.service.additional.DetailsService;
import com.stray.cat.service.additional.IndexService;
import com.stray.cat.service.additional.ReportService;
import com.stray.cat.util.TitleUtil;
import com.stray.cat.vo.CommentPostVo;
import com.stray.cat.vo.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("details")
public class detailsController {

    @Autowired
    IndexService indexService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    DetailsService detailsService;
    @Autowired
    PostService postService;
    @Autowired
    SponsorService sponsorService;
    @Autowired
    ShareService shareService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    HttpSession session;
    @Autowired
    ReportService reportService;
    @Autowired
    SentenceService sentenceService;


    @GetMapping
    public String index(Model model,Integer id,String status){
        int i=-1;
        if(status.equals(IndexFrom.Post.getDesc())){
            i=0;
        }else if(status.equals(IndexFrom.Share.getDesc())){
            i=1;
        } else if(status.equals(IndexFrom.Sponsor.getDesc())){
            i=2;
        }
        session.setAttribute(CatConst.USER_DETAILS_STATUS,i);
        session.setAttribute(CatConst.USER_DETAILS_CODE,id);
        model.addAttribute("comments",detailsService.findComment(i,id));
        detailsService.addCount(id,i);
        model.addAttribute("count",commentMapper.queryCount1(id,i));
        model.addAttribute("recommends",detailsService.findRecommends(i));
        IndexVo info=detailsService.findDetails(id,i);
        model.addAttribute("info",info);
        model.addAttribute("url",TitleUtil.getUrl());
        List<IndexVo> indexMax=indexService.findMaxCount();
        List<Notice> notices=noticeService.queryNotice();
        model.addAttribute("notices",notices);
        model.addAttribute("indexMax",indexMax);
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "details";
    }

    @PostMapping("interceptor/addComment")
    public String comment(String comment,Model model){
        int i= (int) session.getAttribute(CatConst.USER_DETAILS_STATUS);
        int id= (int) session.getAttribute(CatConst.USER_DETAILS_CODE);
        User user= (User) session.getAttribute("user_session");
        Comment comment1=new Comment();
        comment1.setCommentContent(comment);
        comment1.setCommentPhone(user.getUserPhone());
        comment1.setCommentCreatetime(new Date());
        comment1.setCommentPsId(id);
        comment1.setCommentSource(i);
        commentMapper.addComment(comment1);
        model.addAttribute("comments",detailsService.findComment(i,id));
        detailsService.addCount(id,i);
        model.addAttribute("count",commentMapper.queryCount1(id,i));
        model.addAttribute("recommends",detailsService.findRecommends(i));
        IndexVo info=detailsService.findDetails(id,i);
        model.addAttribute("info",info);
        List<IndexVo> indexMax=indexService.findMaxCount();
        List<Notice> notices=noticeService.queryNotice();
        model.addAttribute("notices",notices);
        model.addAttribute("indexMax",indexMax);
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        model.addAttribute("url",TitleUtil.getUrl());
        return "details";
    }

    @PostMapping("interceptor/report")
    public String report(String reason,Model model){
        int i= (int) session.getAttribute(CatConst.USER_DETAILS_STATUS);
        int id= (int) session.getAttribute(CatConst.USER_DETAILS_CODE);
        User user= (User) session.getAttribute("user_session");
        if(reason.equals("")==false){
            Report report=new Report();
            report.setReportUser(user.getUserNickname());
            report.setReportCreatetime(new Date());
            report.setReportContent(reason);
            if(i==0){
                report.setReportFrom("启示");
            }if(i==1){
                report.setReportFrom("分享");
            }if(i==2){
                report.setReportFrom("赞助");
            }
            report.setReportFromId(id);
            reportService.addReport(report);
        }
        model.addAttribute("comments",detailsService.findComment(i,id));
        detailsService.addCount(id,i);
        model.addAttribute("recommends",detailsService.findRecommends(i));
        IndexVo info=detailsService.findDetails(id,i);
        model.addAttribute("info",info);
        List<IndexVo> indexMax=indexService.findMaxCount();
        List<Notice> notices=noticeService.queryNotice();
        model.addAttribute("notices",notices);
        model.addAttribute("indexMax",indexMax);
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        model.addAttribute("url",TitleUtil.getUrl());
        return "details";
    }
}
