package com.gzuniversity.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
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

    //根据视频id获取视频凭证
    @GetMapping("getPathAuth/{videoId}")
    public R getPathAuthById(@PathVariable String videoId){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClientUtil.initVodClient(ConstantAliyunUtils.ACCESS_KEY_ID, ConstantAliyunUtils.ACCESS_KEY_SECRET);
            //创建获取视频凭证的request和response
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response;
            //设置视频id
            request.setVideoId(videoId);
            //调用初始化对象的方法获得凭证
            response=client.getAcsResponse(request);
            System.out.println("playpath:"+response.getPlayAuth());
            return R.ok().data("playAuth",response.getPlayAuth());
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取凭证失败");
        }


    }
}
