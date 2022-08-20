package com.gzuniversity.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.gzuniversity.commonutils.R;
import com.gzuniversity.servicebase.handler.GuliException;
import com.gzuniversity.vod.service.VodService;
import com.gzuniversity.vod.util.ConstantAliyunUtils;
import com.gzuniversity.vod.util.InitVodClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
    //上传视频
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        //输入文件，放回上传视频id
        String videoId=vodService.uploadVideo2Aliyun(file);
        return R.ok().data("videoId",videoId);
    }
    //根据视频id删除视频
    @DeleteMapping("removeAliyunVideo/{videoId}")
    public R removeVideo(@PathVariable String videoId){
        vodService.removeAliyunVideo(videoId);
        return R.ok();
    }
    //根据多个视频id删除多个视频
    //参数传递多个视频id
    @DeleteMapping("deleteBatch")
    public R deleteVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeAliyunVideoList(videoIdList);
        return R.ok();
    }

}
