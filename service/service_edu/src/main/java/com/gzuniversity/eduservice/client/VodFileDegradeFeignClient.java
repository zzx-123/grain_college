package com.gzuniversity.eduservice.client;

import com.gzuniversity.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

//如果VodClient跨服务调用失败，就会执行此处的实现类
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R removeVideo(String videoId) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R deleteVideoList(List<String> videoIdList) {
        return R.error().message("删除视频失败");
    }
}
