package com.gzuniversity.msmservice.controller;

import com.gzuniversity.commonutils.*;
import com.gzuniversity.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //根据电话查看redis中是否已经存在验证码
        String code= redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        param.put("phone", phone);
        boolean isSend = msmService.send(param);
        //发送成功
        if(isSend){
            //将验证码存入Redis中
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);//设置保留时间为5分钟
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
