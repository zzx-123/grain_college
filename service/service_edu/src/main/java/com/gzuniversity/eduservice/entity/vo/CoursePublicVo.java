package com.gzuniversity.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublicVo {
    private String id;//课程id
    private String title;//课程名称
    private String cover;//封面
    private Integer lessonNum;//课程数
    private String subjectLeveOne;//一级分类
    private String subjectLeveTwo;//二级分类
    private String teacherName;//讲师名称
    private String price;//价格

}
