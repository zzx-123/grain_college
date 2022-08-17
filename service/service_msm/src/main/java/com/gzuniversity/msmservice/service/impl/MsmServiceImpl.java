package com.gzuniversity.msmservice.service.impl;

import com.aliyun.teautil.models.RuntimeOptions;
import com.gzuniversity.msmservice.service.MsmService;
import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teaconsole.*;
import com.aliyun.darabonba.env.*;
import com.aliyun.teautil.*;
import com.aliyun.darabonbatime.*;
import com.aliyun.darabonbastring.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
//@Transactional
@Service
public class MsmServiceImpl implements MsmService {

    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
    @Override
    public boolean send(Map<String, Object> param) {
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient("LTAI5tHVrLiuRK6ms2hvt8d7", "zrHELdstFVrPh9KB8k362jCnXdFL7N");
            System.out.println(param.get("phone").toString()+"  "+param.get("code"));
            // 1.发送短信
            SendSmsRequest sendReq = new SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers((String)param.get("phone"))
                    .setTemplateParam("{\"code\":\""+param.get("code")+"\"}");

            SendSmsResponse sendResp = client.sendSms(sendReq);//发送短信
            //检查是否发送成功
            if (sendResp==null||!com.aliyun.teautil.Common.equalString(sendResp.body.code, "OK")) {
                com.aliyun.teaconsole.Client.log("错误信息: " + sendResp.body.message + "");
                return false;
            }
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
}
