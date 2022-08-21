package com.gzuniversity.eduservice.client.impl;

import com.gzuniversity.commonutils.UcenterMemberOrder;
import com.gzuniversity.eduservice.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public UcenterMemberOrder getUcenterById(String memberId) {
        return null;
    }
}
