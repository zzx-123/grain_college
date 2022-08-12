package com.gzuniversity.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzuniversity.commonutils.R;
import com.gzuniversity.eduservice.entity.EduCourse;
import com.gzuniversity.eduservice.entity.vo.CourseInfoVo;
import com.gzuniversity.eduservice.entity.vo.CoursePublicVo;
import com.gzuniversity.eduservice.entity.vo.CourseQueryVo;
import com.gzuniversity.eduservice.service.EduCourseService;
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
// TODO 测试
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //TODO 测试
    //课程列表
    //分页查询
    //page 当前页面   limit 每页记录数
    @GetMapping("{current}/{limit}")
    public R getCourseList(@PathVariable long current,@PathVariable long limit) {
        Page<EduCourse>coursePage=new Page<>(current,limit);
        eduCourseService.page(coursePage,null);
        long total=coursePage.getTotal();
        List<EduCourse> courseList =coursePage.getRecords();
        return R.ok().data("total",total).data("rows", courseList);
    }
    //TODO 测试
    //条件查询分页查询
    @GetMapping("pageCourseQuery/{current}/{limit}")
    public R pageCourseQuery(@PathVariable long current, @PathVariable long limit
            ,@RequestBody(required = false) CourseQueryVo courseQueryVo){
        //创建page对象
        Page<EduCourse>coursePage=new Page<>(current,limit);
        //进行分页查询
        eduCourseService.pageQuery(coursePage,courseQueryVo);
        long total=coursePage.getTotal();
        List<EduCourse> courseList = coursePage.getRecords();
        return R.ok().data("total",total).data("rows",courseList);
    }



    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseID",courseId);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getCoursePublicInfo/{courseId}")
    public R getCoursePublicInfo(@PathVariable String courseId){
        CoursePublicVo coursePublicVo=eduCourseService.getCoursePublicInfo(courseId);
        return R.ok().data("publishCourse",coursePublicVo);

    }

    //课程最终发布，修改课程状态
    @PostMapping("coursePublic/{courseId}")
    public R coursePublic(@PathVariable String courseId){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");//设置课程发布状态
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        boolean remove=eduCourseService.removeCourse(courseId);
        return remove ?R.ok():R.error().message("删除失败");
    }


}

