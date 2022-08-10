package com.gzuniversity.eduservice.controller;


import com.gzuniversity.commonutils.R;
import com.gzuniversity.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    //课程大纲提交
    @GetMapping("getCharterVideo/{courseId}")
    public R getCharterVideo(@PathVariable String courseId){
        return R.ok();
    }

}

