package com.gzuniversity.educenter.service;

import com.gzuniversity.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzuniversity.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-08-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getUserByOpenId(String openid);
}
