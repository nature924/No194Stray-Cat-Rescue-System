package com.stray.cat.controller;

import com.github.pagehelper.PageInfo;
import com.stray.cat.dto.CatConst;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.Sentence;
import com.stray.cat.pojo.User;
import com.stray.cat.service.PostService;
import com.stray.cat.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("revelation")
public class revelationController {
    @Autowired
    PostService postService;
    @Autowired
    SentenceService sentenceService;

    @GetMapping("")
    public String revelation(Model model,HttpSession session,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "limit", defaultValue = "8") int limit){
        model.addAttribute("info",postService.findPageAllPost(page,limit));
        model.addAttribute("search",null);
        model.addAttribute("indexMax",postService.findMaxPost(4));
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "revelation";
    }

    @RequestMapping("search")
    public String indexSearch(String search,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "limit", defaultValue = "8") int limit,Model model){
        PageInfo<Post> info=postService.findPageSearchPost(page,limit,search);

        model.addAttribute("info",info);
        model.addAttribute("search",search);
        model.addAttribute("indexMax",postService.findMaxPost(4));
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "revelation";
    }

    @GetMapping("interceptor/writeRevelation")
    public String write(Model model,HttpSession session,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit){
        model.addAttribute("status",0);
        User user = (User) session.getAttribute(CatConst.USER_SESSION_KEY);
        model.addAttribute("info",postService.findPagePost(page,limit));
        return "revelation_writing";
    }

    @GetMapping("interceptor/writeRevelationStatus")
    public String status(@RequestParam("status") int status, Model model,HttpSession session){
        model.addAttribute("status",status);
        if(status==0){
            return "redirect:/revelation/interceptor/writeRevelation";
        }else {
            return "revelation_writing";
        }
    }

    @PostMapping("interceptor/writePost")
    @ResponseBody
    public JsonResult writePost(Post post, HttpSession session){
       User user= (User) session.getAttribute(CatConst.USER_SESSION_KEY);
       post.setPostPhone(user.getUserPhone());
       post.setPostCreatetime(new Date());
       int i=postService.addPost(post);
       if(i==1){
           return new JsonResult(true,"发布成功！");
       }else {
           return new JsonResult(false, "发布失败！");
       }
    }

    @GetMapping("interceptor/deletePost")
    public String deletePost(@RequestParam("postId") int postId){
        postService.deletePost(postId);
        return "redirect:/revelation/interceptor/writeRevelation";
    }

    @GetMapping("interceptor/updatePost")
    public String updatePost(@RequestParam("postId") int postId,Model model){
        Post post=postService.queryPostById(postId);
        System.out.println(post);
        model.addAttribute("post",post);
        model.addAttribute("status",1);
        return "revelation_writing";
    }

    @PostMapping("interceptor/updatePost")
    @ResponseBody
    public JsonResult getUpdatePost(Post post){
        post.setPostCreatetime(new Date());
        int i=postService.updatePost(post);
        if(i==1){
            return new JsonResult(true,"修改成功！");
        }else {
            return new JsonResult(false, "修改失败！");
        }
    }



}
