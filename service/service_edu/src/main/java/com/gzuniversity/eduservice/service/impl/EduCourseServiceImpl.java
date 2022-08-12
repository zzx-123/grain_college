package com.gzuniversity.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzuniversity.eduservice.entity.EduCourse;
import com.gzuniversity.eduservice.entity.EduCourseDescription;
import com.gzuniversity.eduservice.entity.vo.CourseInfoVo;
import com.gzuniversity.eduservice.entity.vo.CoursePublicVo;
import com.gzuniversity.eduservice.entity.vo.CourseQueryVo;
import com.gzuniversity.eduservice.mapper.EduChapterMapper;
import com.gzuniversity.eduservice.mapper.EduCourseDescriptionMapper;
import com.gzuniversity.eduservice.mapper.EduCourseMapper;
import com.gzuniversity.eduservice.mapper.EduVideoMapper;
import com.gzuniversity.eduservice.service.EduChapterService;
import com.gzuniversity.eduservice.service.EduCourseDescriptionService;
import com.gzuniversity.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzuniversity.eduservice.service.EduVideoService;
import com.gzuniversity.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

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
        courseDescriptionService.save(courseDescription);
        /*if(insert<=0){
            throw new GuliException(20001,"添加失败");
        }*/
        return cid;

    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String course_id) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(course_id);

        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //根据课程id查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(course_id);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update==0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        //修改课程描述表
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublicVo getCoursePublicInfo(String id) {
        //调用mapper
        CoursePublicVo coursePublicInfo = baseMapper.getCoursePublicInfo(id);
        return coursePublicInfo;
    }

    @Override
    public boolean removeCourse(String courseId) {
        //删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //删除课程描述
        courseDescriptionService.removeById(courseId);
        //删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new GuliException(20001,"删除失败");
        }
        return true;
    }

    //分页条件查询

    @Override
    public void pageQuery(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo) {
        //创建条件查询
        QueryWrapper<EduCourse>courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.orderByAsc("gmt_create");//根据创建时间降序
        if(courseQueryVo==null){//如果没有查询条件，则进行分页查询全部
            baseMapper.selectPage(coursePage,courseQueryWrapper);
            return;
        }
        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        //多条件组合查询
        if(!StringUtils.isEmpty(title)){
            courseQueryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            courseQueryWrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            courseQueryWrapper.ge("subjectParentId",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            courseQueryWrapper.ge("subjectId",subjectId);
        }
        baseMapper.selectPage(coursePage,courseQueryWrapper);
        return;
    }
}
