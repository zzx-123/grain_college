package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author edu
 * @since 2022-08-12
 */
public interface EduChapterService extends IService<EduChapter> {

    public List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
