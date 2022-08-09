package com.gzuniversity.educenter.controller;


import com.gzuniversity.commonutils.JwtUtils;
import com.gzuniversity.commonutils.R;
import com.gzuniversity.educenter.entity.UcenterMember;
import com.gzuniversity.educenter.entity.vo.RegisterVo;
import com.gzuniversity.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-08-08
 */
@RestController
@RequestMapping("/educenter/ucenterMember")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember){//传入手机号和密码
        //返回token值，使用Jwt生成
        String token=ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }
    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        //调用jwt工具类对象，根据request对象获取头部信息，返回用户id
        String memberId = JwtUtils.getUserIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",ucenterMember);
    }
}

