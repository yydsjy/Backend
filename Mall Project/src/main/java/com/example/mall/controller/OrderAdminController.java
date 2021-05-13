package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.service.OrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
public class OrderAdminController {
    @Autowired
    OrderService orderService;
    @ApiOperation("list order for admin")
    @GetMapping("/list")
    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("admin deliver")
    @PostMapping("/deliver")
    public ApiRestResponse deliver(@RequestParam String orderNo){
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }
}
