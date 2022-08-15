package com.gzuniversity.vodteset;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class VodTest {
    public static void main(String[] args)throws Exception {
        testUploadVideo("LTAI5tFdmJMK6ivuqUVCvD5H"
                , "lLfpu26Fzbq5hHkM84HaXcHImFEpKH"
                ,"测试上传视频通过sdk"
                ,"D:/暑假学习/java笔记/movie.mp4");

    }



    /**
     * 本地文件上传接口
     *
     * @param accessKeyId  密钥id
     * @param accessKeySecret   密钥
     * @param title  上传文件后的名称
     * @param fileName   本地文件的路径
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        //System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    //根据视频id获取播放凭证
    public static void getPlayAuthById()throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tFdmJMK6ivuqUVCvD5H", "lLfpu26Fzbq5hHkM84HaXcHImFEpKH");
        //创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response=new GetVideoPlayAuthResponse();
        //设置视频id
        request.setVideoId("032957801e624296befb4c5bbb2ce96f");
        //调用初始化对象的方法获得凭证
        response=client.getAcsResponse(request);
        System.out.println("playpath:"+response.getPlayAuth());

    }
    //根据视频id获取视频播放地址
    public static void getPlayUrlById()throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tFdmJMK6ivuqUVCvD5H", "lLfpu26Fzbq5hHkM84HaXcHImFEpKH");
        //创建获取视频地址request和response
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId("032957801e624296befb4c5bbb2ce96f");
        //调用初始化对象里面的方法。传递request获取数据
        response=client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
    }
}
