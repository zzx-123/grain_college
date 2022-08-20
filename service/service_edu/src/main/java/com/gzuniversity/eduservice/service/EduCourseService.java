package com.gzuniversity.eduservice.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzuniversity.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzuniversity.eduservice.entity.vo.CourseInfoVo;
import com.gzuniversity.eduservice.entity.vo.CoursePublicVo;
import com.gzuniversity.eduservice.entity.vo.CourseQueryVo;
import com.gzuniversity.eduservice.entity.vo.frontvo.CourseWebVo;
import com.gzuniversity.eduservice.frontvo.CourseFrontVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String course_id);


    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublicVo getCoursePublicInfo(String id);

    boolean removeCourse(String courseId);

    void pageQuery(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
