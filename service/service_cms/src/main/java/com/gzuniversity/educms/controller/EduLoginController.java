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

        return R.ok().data("roles", "[admin]").data("name", "1234567").data("avatar", "https://i0.hdslb.com/bfs/face/cf0737f1c4c7516e702b41687f4f7c7e368a683a.jpg@240w_240h_1c_1s.webp");
    }
}
