package com.gzuniversity.eduservice.service.impl;

import com.gzuniversity.eduservice.entity.EduCourse;
import com.gzuniversity.eduservice.entity.EduCourseDescription;
import com.gzuniversity.eduservice.entity.vo.CourseInfoVo;
import com.gzuniversity.eduservice.mapper.EduCourseDescriptionMapper;
import com.gzuniversity.eduservice.mapper.EduCourseMapper;
import com.gzuniversity.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzuniversity.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionMapper courseDescriptionMapper;
    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {

        //向课程表添加课程基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new GuliException(20001,"添加课程基本信息失败");
        }
        //获得课程的id作为课程简介的id
        String cid=eduCourse.getId();
        //向课程简介表添加课程描述
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setDescription(courseDescription.getDescription());
        courseDescription.setId(cid);
        insert = courseDescriptionMapper.insert(courseDescription);
        if(insert<=0){
            throw new GuliException(20001,"添加失败");
        }

    }
}
