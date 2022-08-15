package com.gzuniversity.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVideo2Aliyun(MultipartFile file);

    void removeAliyunVideo(String videoId);
}
