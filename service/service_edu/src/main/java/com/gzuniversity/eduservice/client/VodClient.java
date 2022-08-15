package com.gzuniversity.eduservice.client;

import com.gzuniversity.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-vod")
public interface VodClient {
    //定义调用方法路径
    //根据视频id删除视频
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);
}
