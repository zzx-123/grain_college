package com.gzuniversity.eduservice.controller;


import com.gzuniversity.commonutils.R;
import com.gzuniversity.eduservice.entity.EduChapter;
import com.gzuniversity.eduservice.entity.EduCourse;
import com.gzuniversity.eduservice.entity.vo.CourseInfoVo;
import com.gzuniversity.eduservice.entity.vo.chater.ChapterVo;
import com.gzuniversity.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
//TODO 测试
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    //课程大纲提交，根据课程id进行查询
    @GetMapping("getCharterVideo/{courseId}")
    public R getCharterVideo(@PathVariable String courseId){
        List<ChapterVo> chapterVoList= eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",chapterVoList);
    }

    //添加章节
    @PostMapping("addCharter")
    public R addCharter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }
    //查询章节id
    @GetMapping("getCharterInfo/{charterId}")
    public R getCharterInfo(@PathVariable String charterId){
        EduChapter eduChapter = eduChapterService.getById(charterId);
        return R.ok().data("charter",eduChapter);
    }

    //修改章节
    @GetMapping("updateCharter")
    public R updateCharterInfo(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }
    //删除章节
    @DeleteMapping("{charterId}")
    public R deleteCharter(@PathVariable String charterId){
        //若章节里面有小节，拒接删除
        boolean isDelete=eduChapterService.deleteCharterById(charterId);
        return isDelete?R.ok():R.error();
    }

}

