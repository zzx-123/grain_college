package com.gzuniversity.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

@Data
public class CourseQueryVo {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "教师Id")
    private String teacherId;
    @ApiModelProperty(value = "一章节Id")
    private String subjectParentId;
    @ApiModelProperty(value = "小节Id")
    private String subjectId;
}
