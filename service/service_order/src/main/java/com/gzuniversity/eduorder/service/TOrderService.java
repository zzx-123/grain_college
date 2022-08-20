package com.gzuniversity.eduorder.service;

import com.gzuniversity.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-16
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String userIdByJwtToken);
}
