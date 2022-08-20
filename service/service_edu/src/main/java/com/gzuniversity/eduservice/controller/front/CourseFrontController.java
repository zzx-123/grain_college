package com.gzuniversity.eduservice.controller.front;


import com.gzuniversity.commonutils.R;
import com.gzuniversity.eduservice.entity.EduCourse;
import com.gzuniversity.eduservice.entity.vo.chater.ChapterVo;
import com.gzuniversity.eduservice.entity.vo.frontvo.CourseWebVo;
import com.gzuniversity.eduservice.frontvo.CourseFrontVo;
import com.gzuniversity.eduservice.service.EduChapterService;
import com.gzuniversity.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService CourseService;

    @Autowired
    private EduChapterService chapterService;

    @PostMapping("getFrontInfo/{page}/{limit}")//@RequestBody(required = false)传入值可以为空
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Page<EduCourse> pageCourse = new Page<>(page,limit);

        Map<String,Object> map = CourseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }
    //课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = CourseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程Id和用户ID查询订单表中是否支付
//        String memberId = JwtUtils.getMemberIdByJwtToken(request);//取出用户id
//        boolean buyCourse = orderClient.isBuyCourse(courseId, memberId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
//        .data("isBuy",buyCourse)
    }
}
