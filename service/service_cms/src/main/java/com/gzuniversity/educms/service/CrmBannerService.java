package com.gzuniversity.educms.service;

import com.gzuniversity.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-05
 */
public interface CrmBannerService extends IService<CrmBanner> {
    List<CrmBanner> selectAllBanner();
}
