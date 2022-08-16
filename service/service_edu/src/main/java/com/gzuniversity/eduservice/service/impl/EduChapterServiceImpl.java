package com.gzuniversity.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzuniversity.eduservice.entity.EduChapter;
import com.gzuniversity.eduservice.entity.EduVideo;
import com.gzuniversity.eduservice.entity.vo.chater.ChapterVo;
import com.gzuniversity.eduservice.entity.vo.chater.VideoVo;
import com.gzuniversity.eduservice.mapper.EduChapterMapper;
import com.gzuniversity.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzuniversity.eduservice.service.EduVideoService;
import com.gzuniversity.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter>chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter>eduChapterList=baseMapper.selectList(chapterQueryWrapper);
        //根据课程id查询课程里面所有小节
        QueryWrapper<EduVideo>videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo>eduVideoList=eduVideoService.list(videoQueryWrapper);
        //创建list集合用于最终封装
        List<ChapterVo>chapterVoList=new ArrayList<>();
        //遍历所有章节集合进行封装
        for(EduChapter eduChapter:eduChapterList){
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVoList.add(chapterVo);
            //创建小节List集合
            List<VideoVo>videoVoList=new ArrayList<>();
            //查询所有小节集合进行封装
            for(EduVideo eduVideo:eduVideoList){
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVoList);
        }

        return chapterVoList;
    }

    //删除章节，若有小节拒绝删除
    @Override
    public boolean deleteCharterById(String charterId) {
        //根据id查小节，若存在小节则不删除
        QueryWrapper<EduVideo>chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("charter_id",charterId);
        int count = eduVideoService.count(chapterQueryWrapper);
        //查出小节，不进行删除
        if(count>0){
            throw new GuliException(20001,"拒绝删除");
        }
        //没有小节进行删除
        int delete = baseMapper.deleteById(charterId);
        return delete>0;
    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        int delete = baseMapper.delete(queryWrapper);
        return delete==1;
    }

}
