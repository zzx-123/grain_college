package com.gzuniversity.eduservice.client;

import com.gzuniversity.commonutils.UcenterMemberOrder;
import com.gzuniversity.eduservice.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/educenter/member/getUcenter/{memberId}")
    public UcenterMemberOrder getUcenterById(@PathVariable("memberId") String memberId);
}
