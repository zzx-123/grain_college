package com.gzuniversity.eduservice.service;

import com.gzuniversity.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzuniversity.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVo courseInfoVo);
}
