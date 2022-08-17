package com.gzuniversity.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo2Aliyun(MultipartFile file);

    void removeAliyunVideo(String videoId);

    void removeAliyunVideoList(List<String> videoIdList);
}
