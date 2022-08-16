package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author edu
 * @since 2022-08-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseWebVo getBaseCourseInfo(String courseId);
}
