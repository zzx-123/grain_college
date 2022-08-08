package com.gzuniversity.educms.controller;


import com.gzuniversity.commonutils.R;
import com.gzuniversity.educms.entity.CrmBanner;
import com.gzuniversity.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-05
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> crmBanners = bannerService.selectAllBanner();
        return R.ok().data("list", crmBanners);
    }


}

