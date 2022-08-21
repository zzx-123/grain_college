package com.gzuniversity.educenter.controller;


import com.google.gson.Gson;
import com.gzuniversity.commonutils.JwtUtils;
import com.gzuniversity.educenter.entity.UcenterMember;
import com.gzuniversity.educenter.service.UcenterMemberService;
import com.gzuniversity.educenter.util.ConstantWxUtils;
import com.gzuniversity.educenter.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller//只请求地址，不返回数据,重定向地址
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("login")
    public String getWxCode(){
        //微信开发平台生成二维码确定
        //%s是占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncode编码，授权码需要进行加密

        //System.out.println(ConstantWxUtils.WX_OPEN_APP_ID+ConstantWxUtils.WX_OPEN_APP_SECRET);
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;//获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");//url编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "gzuniversity"
        );
        //System.out.println(redirectUrl);
        //请求微信地址,重定向的方式
        return "redirect:" + url;
    }
    //这个是老师的域名地址里做的重定向 http://localhost:8160/api/ucenter/wx/callback，进而获得微信登录信息
    /*
    * code:临时票据
    * state:原样传递
    * */
    @GetMapping("callback")
    public String callback(String code ,String state) {
        try {
            //用code请求微信服务器
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数：id、密钥和 code
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //用httpclient发送请求拼接好的地址获取access_token和openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);//json格式需要进行转换
            //从accessTokenInfo获取数据，将其转为map集合，根据key获得value
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //把扫码人信息加入数据库里
            //判断数据表里是否存在相同的微信信息
            UcenterMember user = ucenterMemberService.getUserByOpenId(openid);
            if (user == null) {//如果user为空，则表里没有相同微信数据，进行添加
                //用access_token和openid再去请求微信服务器
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                //获取昵称和头像
                String nickname = (String) userInfoMap.get("nickname");//昵称
                String headimgurl = (String) userInfoMap.get("headimgurl");//头像
                System.out.println("UserInfo" + userInfo);
                //添加用户信息进行注册
                user = new UcenterMember();
                user.setOpenid(openid);
                user.setNickname(nickname);
                user.setAvatar(headimgurl);
                ucenterMemberService.save(user);
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickname());
            //返回首页界面
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            //
            return null;
        }

    }
/*
    //获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session){
        try {
        //1、得到授权临时票据code

        //2、拿着code请求 微信的固定地址，得到两个值access_token和openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);
        //请求这个拼接好的地址，得到返回两个值access_token和openid
        //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //使用gson转换工具Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);

            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");


            //判断该微信信息是否注册过
            UcenterMemberOrder menber = ucenterMemberService.getMenberByOperid(openid);
            if (menber == null){
                //3\拿着access_token和openid，再去请求微信提供的固定地址，获取扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                //再次拼接微信地址
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

                String userInfo = HttpClientUtils.get(userInfoUrl);

                //获取的微信个人信息json信息进行转换
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname");//昵称
                String headimgurl = (String)userInfoMap.get("headimgurl");//头像

                //把微信信息注册到数据库中
                menber = new UcenterMemberOrder();
                menber.setNickname(nickname);
                menber.setOpenid(openid);
                menber.setAvatar(headimgurl);
                ucenterMemberService.save(menber);
            }

            //使用jwt生成token字符串
            String jwtToken = JwtUtils.getJwtToken(menber.getId(), menber.getNickname());
            //返回首页面
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new GuliException(20001,"登录失败");
            return null;
        }
    }*/


}
