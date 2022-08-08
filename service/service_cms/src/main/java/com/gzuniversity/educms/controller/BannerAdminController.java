package com.gzuniversity.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzuniversity.commonutils.R;
import com.gzuniversity.educms.entity.CrmBanner;
import com.gzuniversity.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-05
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        return R.ok().data("item", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestParam CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }
    //删除banner
    @ApiOperation(value = "删除banner")
    @DeleteMapping("remove/{id}")
    public R removeBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.ok();
    }
    //修改banner
    @ApiOperation(value = "修改banner")
    @PutMapping("update")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok();
    }
    //查询banner
    @ApiOperation(value = "获取banner")
    @PostMapping("get/{id}")
    public R getBanner(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

}

