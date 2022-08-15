package com.gzuniversity.eduservice.controller;


import com.gzuniversity.commonutils.R;
import com.gzuniversity.eduservice.client.VodClient;
import com.gzuniversity.eduservice.entity.EduVideo;
import com.gzuniversity.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-08-10
 */
//TODO 测试
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;


    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        return save?R.ok():R.error().message("删除失败");
    }
    //删除小节--videoId是小节id不是小节视频的id
    @DeleteMapping("{videoId}")
    public R deleteVideo(
            @ApiParam(name = "videoId", value = "小节id", required = true)
            @PathVariable String videoId){
        boolean flag=eduVideoService.deleteVideoById(videoId);
        return flag?R.ok():R.error().message("删除失败");
    }
    //修改小节
}

