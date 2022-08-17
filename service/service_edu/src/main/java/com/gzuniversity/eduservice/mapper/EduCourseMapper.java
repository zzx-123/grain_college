package com.gzuniversity.eduservice.mapper;

import com.gzuniversity.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gzuniversity.eduservice.entity.vo.CoursePublicVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublicVo getCoursePublicInfo(String courseId);

}
