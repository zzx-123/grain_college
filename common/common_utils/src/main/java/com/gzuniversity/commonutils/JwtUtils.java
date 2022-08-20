package com.gzuniversity.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {
    public static final long EXPIRE=1000*60*60*24;//token过期时间
    public static final String APP_SECRET="123456";//签名密钥
    //用JWT生成加密字符串
    public static String getJwtToken(String id,String nickname){
        String JwtToken= Jwts.builder()
                //设置头部信息
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //设置分类
                .setSubject("GrainCollege-User")
                //设置过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                //设置token主体部分，存储用户信息
                .claim("id",id)
                .claim("nickname",nickname)
                //生成加密字符串
                .signWith(SignatureAlgorithm.HS256,APP_SECRET)
                .compact();
        return JwtToken;
    }
    //判断token是否存在且有效
    public static boolean checkToken(String jwtToken){
        if(StringUtils.isEmpty(jwtToken))return false;
        try {
            //根据密钥验证是否有效
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //判断token是否存在且有效
    public static boolean checkToken(HttpServletRequest request){
        try {
            String jwtToken=request.getHeader("token");//从请求中获取token字符串
            //根据密钥验证是否有效
            if(StringUtils.isEmpty(jwtToken))return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //从请求中获取用户id
    public static String getUserIdByJwtToken(HttpServletRequest request){
        String jwtToken=request.getHeader("token");
        if(checkToken(jwtToken))return "";
        //通过解析获取Claims
        Jws<Claims> claimsJws=Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims=claimsJws.getBody();//获取Claims中的主体
        return (String)claims.get("id");
    }

    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
