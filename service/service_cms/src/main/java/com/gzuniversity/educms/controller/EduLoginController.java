package com.gzuniversity.educms.controller;

import com.gzuniversity.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/educms/user")
@CrossOrigin(allowCredentials = "true")
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        System.out.println("login");
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info(){

        return R.ok().data("roles", "[admin]").data("name", "1234567").data("avatar", "https://scpic.chinaz.net/files/pic/moban/202111/moban5910.jpg");
    }
}
