package com.example.mall.service;

import com.example.mall.model.request.CreateOrderReq;
import com.example.mall.model.vo.OrderVO;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo);

    PageInfo listForAdmin(Integer pageName, Integer pageSize);

    void pay(String orderNo);

    void deliver(String orderNo);

    void finish(String orderNo);
}
