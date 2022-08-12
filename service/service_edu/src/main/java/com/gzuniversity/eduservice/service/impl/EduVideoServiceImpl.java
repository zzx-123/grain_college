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

    //TODO 还要删除小节里面的视频
    @Override
    public boolean removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
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
