package com.gzuniversity.eduservice.client;

import com.gzuniversity.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("service-vod")
public interface VodClient {
    //定义调用方法路径
    //根据视频id删除视频
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);

    //根据多个视频id删除多个视频
    //参数传递多个视频id
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
