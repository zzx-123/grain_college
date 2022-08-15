package com.gzuniversity.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzuniversity.eduservice.client.VodClient;
import com.gzuniversity.eduservice.entity.EduVideo;
import com.gzuniversity.eduservice.mapper.EduVideoMapper;
import com.gzuniversity.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //注入vodclient
    @Autowired
    private VodClient vodClient;

    //TODO 测试
    @Override
    public boolean removeVideoByCourseId(String courseId) {
        //根据课程id查出所有视频的id
        QueryWrapper<EduVideo>eduVideoQueryWrapper=new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        eduVideoQueryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(eduVideoQueryWrapper);

        List<String>videoIds=new ArrayList<>();
        for(EduVideo eduVideo:eduVideoList){
            String videoSourceId=eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }

        }
        if(videoIds.size()>0){
            //根据多个视频id删除多个视频
            vodClient.deleteVideoList(videoIds);
        }


        eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        int delete = baseMapper.delete(eduVideoQueryWrapper);
        return delete == 1;
    }

    @Override
    public boolean deleteVideoById(String videoId) {

        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        //删除视频资源
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(videoSourceId);
        }
        //删除小节
        int result = baseMapper.deleteById(videoId);
        return result > 0;
    }
}
