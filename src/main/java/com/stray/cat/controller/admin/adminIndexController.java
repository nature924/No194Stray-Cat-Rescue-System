package com.stray.cat.controller.admin;

import com.stray.cat.dto.CatConst;
import com.stray.cat.service.additional.AdminIndexService;
import com.stray.cat.vo.AdminIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("admin")
public class adminIndexController {
    @Autowired
    AdminIndexService adminIndexService;
    @Autowired
    HttpSession session;

    @GetMapping("")
    public String index(Model model){
        List<AdminIndexVo> adminIndexVos=adminIndexService.queryAll();
        model.addAttribute("lists",adminIndexVos);
        return "admin/admin_index";
    }

}
