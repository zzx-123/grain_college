package com.gzuniversity.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzuniversity.commonutils.*;
import com.gzuniversity.servicebase.*;
import com.gzuniversity.educenter.entity.UcenterMember;
import com.gzuniversity.educenter.entity.vo.RegisterVo;
import com.gzuniversity.educenter.mapper.UcenterMemberMapper;
import com.gzuniversity.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzuniversity.servicebase.handler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-08-08
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Autowired
    private RedisTemplate<String,String>redisTemplate;
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        //非空校验
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            //System.out.println("账号或密码错误");
            throw new GuliException(20001,"账号或密码为空");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember>memberQueryWrapper=new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        UcenterMember searchUcenterMember = baseMapper.selectOne(memberQueryWrapper);
        //查看是否为手机号空
        if(searchUcenterMember==null){
            //System.out.println("账号或密码错误");
            throw new GuliException(20001,"账号或密码错误");
        }
        //判断密码是否有误
        //密码一般不用明文存储，加密后再存
        //把输入的密码加密后，在与数据库密码比较
        //加密方式MD5
        if(!MD5.encrypt(password).equals(searchUcenterMember.getPassword())){
            //System.out.println("账号或密码错误");
            throw new GuliException(20001,"账号或密码错误");
        }
        //判断用户是否被禁用
        if(searchUcenterMember.getIsDisabled()){
            //System.out.println("账号或密码错误");
            throw new GuliException(20001,"该账户已被封禁");
        }
        System.out.println(searchUcenterMember.getNickname());
        //登录成功，生成jwt工具类
        String jwtToken = JwtUtils.getJwtToken(searchUcenterMember.getId(), searchUcenterMember.getNickname());
        return jwtToken;
    }
    //注册方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //非空校验
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            //System.out.println("账号或密码错误");
            throw new GuliException(20001,"账号或密码错误");
        }

        //判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            //System.out.println("验证码错误，注册失败");
            throw new GuliException(20001,"验证码错误");
        }
        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer selectCount = baseMapper.selectCount(queryWrapper);
        if(selectCount>0){
            //System.out.println("账户已存在");
            throw new GuliException(20001,"账户已存在");
        }
        //数据添加数据库中
        UcenterMember ucenterMember=new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("");//默认头像

        baseMapper.insert(ucenterMember);

    }

    @Override
    public UcenterMember getUserByOpenId(String openid) {
        QueryWrapper<UcenterMember>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember user=baseMapper.selectOne(queryWrapper);
        return user;
    }
}
