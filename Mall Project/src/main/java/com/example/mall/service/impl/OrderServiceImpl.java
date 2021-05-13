package com.example.mall.service.impl;

import com.example.mall.common.Constant;
import com.example.mall.exception.BusinessException;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.filter.UserFilter;
import com.example.mall.model.dao.CartMapper;
import com.example.mall.model.dao.OrderItemMapper;
import com.example.mall.model.dao.OrderMapper;
import com.example.mall.model.dao.ProductMapper;
import com.example.mall.model.pojo.Order;
import com.example.mall.model.pojo.OrderItem;
import com.example.mall.model.pojo.Product;
import com.example.mall.model.request.CreateOrderReq;
import com.example.mall.model.vo.CartVO;
import com.example.mall.model.vo.OrderItemVO;
import com.example.mall.model.vo.OrderVO;
import com.example.mall.service.CartService;
import com.example.mall.service.OrderService;
import com.example.mall.service.UserService;
import com.example.mall.util.OrderCodeFactory;
import com.example.mall.util.QRCodeGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${file.upload.ip}")
    String ip;

    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderReq createOrderReq){
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.list(userId);
//        List<CartVO> cartVOListTemp = new ArrayList<>();
//        for (CartVO cartVO:cartVOList){
//            if (cartVO.getSelected().equals(Constant.Cart.SELECTED)) {
//                cartVOListTemp.add(cartVO);
//            }
//        }

        cartVOList = cartVOList.stream()
                .filter(cartVO -> cartVO.getSelected()==Constant.Cart.SELECTED)
                .collect(Collectors.toList());


        if (cartVOList.isEmpty()){
            throw new BusinessException(ExceptionEnum.EMPTY_CART);
        }

        validSaleStatusAndStock(cartVOList);

        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);

        updateProductStock(orderItemList);

        updateCart(cartVOList);

        Order order = new Order();

        String orderNo = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(cartVOList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setOrderStatus(Constant.OrderStatusEnum.UNPAID.getCode());
        order.setPostage(0);
        order.setPaymentType(1);
        orderMapper.insertSelective(order);

        orderItemList.forEach(orderItem -> {
            orderItem.setOrderNo(orderNo);
            orderItemMapper.insertSelective(orderItem);
        });

        return orderNo;

    }

    private Integer totalPrice(List<CartVO> cartVOList) {
        Integer total = 0;
        for (CartVO cartVO:cartVOList){
            total += cartVO.getTotalPrice();
        }
        return total;
    }

    private void updateCart(List<CartVO> cartVOList) {
        for (CartVO cartVO:cartVOList){
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    private void updateProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem:orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            Integer stock = product.getStock()-orderItem.getQuantity();
            if (stock < 0) {
                throw new BusinessException(ExceptionEnum.LACK_PRODUCT);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartVO cartVO:cartVOList){
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setUnitPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private void validSaleStatusAndStock(List<CartVO> cartVOList) {
        for (CartVO cartVO: cartVOList){
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new BusinessException(ExceptionEnum.NEED_PRODUCT);
            }

            if (cartVO.getQuantity()>product.getStock()){
                throw new BusinessException(ExceptionEnum.LACK_PRODUCT);
            }
        }
    }

    @Override
    public OrderVO detail(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order==null){
            throw new BusinessException(ExceptionEnum.UNFOUND_ORDER);
        }
        Integer userId = UserFilter.currentUser.getId();
        if (!order.getUserId().equals(userId)){
            throw new BusinessException(ExceptionEnum.WRONG_ORDER);
        }
        return getOrderVO(order);


    }

    private OrderVO getOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        orderItemList.forEach(orderItem -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem,orderItemVO);
            orderItemVOList.add(orderItemVO);
        });
        orderVO.setOrderItemVOList(orderItemVOList);
        orderVO.setOrderStatusName(Constant.OrderStatusEnum.valueOf(orderVO.getOrderStatus()).getValue());
        return orderVO;
    }

    @Override
    public PageInfo listForCustomer(Integer pageNum, Integer pageSize){
        Integer userId = UserFilter.currentUser.getId();
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectForCustomer(userId);
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        return new PageInfo(orderVOList);

    }

    private List<OrderVO> orderListToOrderVOList(List<Order> orderList) {
        List<OrderVO> orderVOList = new ArrayList<>();
        orderList.forEach(order -> orderVOList.add(getOrderVO(order)));
        return orderVOList;
    }

    @Override
    public void cancel(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order==null){
            throw new BusinessException(ExceptionEnum.UNFOUND_ORDER);
        }
        Integer userId = UserFilter.currentUser.getId();
        if (!order.getUserId().equals(userId)){
            throw new BusinessException(ExceptionEnum.WRONG_ORDER);
        }
        if (order.getOrderStatus()==Constant.OrderStatusEnum.UNPAID.getCode())  {
            order.setOrderStatus(Constant.OrderStatusEnum.CANCELED.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new BusinessException(ExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public String qrcode(String orderNo){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String address = ip +":"+ request.getLocalPort();
        String payUrl = "http://"+address+"/pay?orderNo="+orderNo;
        try {
            QRCodeGenerator.generateQRCodeImage(payUrl,350,350,Constant.FILE_UPLOAD_DIR+orderNo+".png");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pngAddress = "http://"+address+"/images/"+orderNo+".png";
        return pngAddress;
    }

    @Override
    public PageInfo listForAdmin(Integer pageName, Integer pageSize){
        PageHelper.startPage(pageName,pageSize);
        List<Order> orderList = orderMapper.selectForAdmin();
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        return new PageInfo(orderVOList);
    }

    @Override
    public void pay(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ExceptionEnum.UNFOUND_ORDER);
        }

        if (order.getOrderStatus()== Constant.OrderStatusEnum.UNPAID.getCode()){

            order.setOrderStatus(Constant.OrderStatusEnum.PAID.getCode());
            order.setPayTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new BusinessException(ExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public void deliver(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ExceptionEnum.UNFOUND_ORDER);
        }

        if (order.getOrderStatus()== Constant.OrderStatusEnum.PAID.getCode()){

            order.setOrderStatus(Constant.OrderStatusEnum.DELIVERED.getCode());
            order.setDeliveryTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new BusinessException(ExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public void finish(String orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ExceptionEnum.UNFOUND_ORDER);
        }


        if (!userService.checkAdminRole(UserFilter.currentUser) && !UserFilter.currentUser.getId().equals(order.getUserId())) {
            throw new BusinessException(ExceptionEnum.WRONG_ORDER);
        }


        if (order.getOrderStatus()== Constant.OrderStatusEnum.DELIVERED.getCode()){

            order.setOrderStatus(Constant.OrderStatusEnum.FINISHED.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new BusinessException(ExceptionEnum.WRONG_ORDER_STATUS);
        }
    }
}
