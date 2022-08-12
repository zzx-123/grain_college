package com.gzuniversity.eduservice.service;

import com.gzuniversity.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzuniversity.eduservice.entity.vo.chater.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> getChapterVideoByCourseId(String courseId);


    boolean deleteCharterById(String charterId);


    boolean removeChapterByCourseId(String courseId);
}
