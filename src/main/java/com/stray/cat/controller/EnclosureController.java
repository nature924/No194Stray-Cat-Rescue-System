package com.stray.cat.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.stray.cat.dto.JsonResult;
import com.stray.cat.dto.CatConst;
import com.stray.cat.pojo.Enclosure;
import com.stray.cat.pojo.Sentence;
import com.stray.cat.pojo.User;
import com.stray.cat.service.EnclosureService;
import com.stray.cat.service.SentenceService;
import com.stray.cat.util.StarUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("enclosure")
public class EnclosureController {
    @Autowired
    EnclosureService enclosureService;
    @Autowired
    SentenceService sentenceService;

    @GetMapping("openChoice/{id}")
    public String openChoice(Model model, @PathVariable String id,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "limit", defaultValue = "18") int limit) {
        PageInfo<Enclosure> lists = enclosureService.findPageEnclosure(page, limit);
        model.addAttribute("info", lists);
        model.addAttribute("id", id);
        Sentence sentence=sentenceService.findSentence();
        model.addAttribute("sentence",sentence);
        return "part/open_choice";
    }

    @RequestMapping("upload")
    @ResponseBody
    public JsonResult upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,HttpSession session) {
        return uploadPicture(file,request,session);
    }

    //上传
    public JsonResult uploadPicture(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,HttpSession session) {
        if (!file.isEmpty()) {
            try {
                // 获取用户目录
                String userPath = System.getProperties().getProperty("user.home") + "/cat/";
                // 保存目录
                StringBuffer hold = new StringBuffer("upload/");
                // 获取时间，以年月创建目录
                Date date = DateUtil.date();
                hold.append(DateUtil.thisYear()).append("/").append(DateUtil.thisMonth() + 1).append("/");
                File mediaPath = new File(userPath, hold.toString());
                // 如果没有该目录则创建
                if (!mediaPath.exists()) {
                    mediaPath.mkdirs();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                // 生成文件名称
                String nameSuffix = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."))
                        .replaceAll(" ", "_").replaceAll(",", "") + format.format(DateUtil.date())
                        + new Random().nextInt(1000);
                // 文件后缀
                String fileSuffix = file.getOriginalFilename()
                        .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                // 上传文件名加后缀
                String fileName = nameSuffix + "." + fileSuffix;

                // 转存文件
                file.transferTo(new File(mediaPath.toString(), fileName));

                // 原图片路径
                StringBuffer originalPath = new StringBuffer();
                originalPath.append(mediaPath.getAbsolutePath()).append("/").append(fileName);
                // 压缩图片路径
                StringBuffer compressPath = new StringBuffer();
                compressPath.append(mediaPath.getAbsolutePath()).append("/").append(nameSuffix).append("_small.")
                        .append(fileSuffix);
                // 压缩图片
                Thumbnails.of(originalPath.toString()).size(256, 256).keepAspectRatio(false).toFile(compressPath.toString());
                // 原图数据库路径
                StringBuffer originalDataPath = new StringBuffer();
                originalDataPath.append("/").append(hold).append(fileName);
                // 压缩图数据库路径
                StringBuffer compressDataPath = new StringBuffer();
                compressDataPath.append("/").append(hold).append(nameSuffix).append("_small.").append(fileSuffix);
                User user = (User) session.getAttribute(CatConst.USER_SESSION_KEY);
                // 添加数据库
                Enclosure enclosure = new Enclosure();
                enclosure.setEnclosureUrl(originalDataPath.toString());
                enclosure.setEnclosureSmallUrl(compressDataPath.toString());
                enclosure.setEnclosureType(1);
                enclosure.setEnclosureCreatetime(new Date());
                enclosure.setEnclosureNumber(user.getUserPhone());
                enclosure.setEnclosureWh(StarUtil.getImageWh(new File(mediaPath.toString() + "/" + fileName)));
                enclosureService.addEnclosure(enclosure);
            } catch (Exception e) {
                //log.error("上传附件错误" + e.getMessage());
                return new JsonResult(false, "系统未知错误");
            }
        } else {
            return new JsonResult(false, "文件不能为空");
        }
        return new JsonResult(true, "上传成功");
    }

}
