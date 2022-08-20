package com.gzuniversity.eduorder.controller;


import com.gzuniversity.commonutils.JwtUtils;
import com.gzuniversity.commonutils.R;
import com.gzuniversity.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-16
 */
@RestController
@RequestMapping("/eduorder/t-order")
@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService tOrderService;

    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){

        String orderID = tOrderService.createOrders(courseId, JwtUtils.getUserIdByJwtToken(request));
        return R.ok().data("orderId", orderID);
    }
}

