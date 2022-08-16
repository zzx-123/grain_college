package com.gzuniversity.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.gzuniversity.commonutils.R;
import com.gzuniversity.servicebase.handler.GuliException;
import com.gzuniversity.vod.service.VodService;
import com.gzuniversity.vod.util.ConstantAliyunUtils;
import com.gzuniversity.vod.util.InitVodClientUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo2Aliyun(MultipartFile file) {
        try {
            //fileName:上传文件原始名称
            // xxx.mp4
            String fileName= file.getOriginalFilename();
            if(fileName.isEmpty()){
                throw new GuliException(20001,"上传文件名为空");
            }
            //title:上传后的视频名称
            // xxx
            String title=fileName.substring(0,fileName.lastIndexOf("."));

            //inputStream:上传文件输入流
            InputStream inputStream= file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantAliyunUtils.ACCESS_KEY_ID,
                    ConstantAliyunUtils.ACCESS_KEY_SECRET,
                    title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID

            String videoId=null;

            if (response.isSuccess()) {
                videoId=response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeAliyunVideo(String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClientUtil.initVodClient(ConstantAliyunUtils.ACCESS_KEY_ID, ConstantAliyunUtils.ACCESS_KEY_SECRET);
            //创建一个删除request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        }catch (Exception e){
            e.printStackTrace();;
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @Override
    public void removeAliyunVideoList(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClientUtil.initVodClient(ConstantAliyunUtils.ACCESS_KEY_ID, ConstantAliyunUtils.ACCESS_KEY_SECRET);
            //创建一个删除request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //拼接多个id
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        }catch (Exception e){
            e.printStackTrace();;
            throw new GuliException(20001,"删除视频失败");
        }
    }

}
