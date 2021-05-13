package com.example.mall.service.impl;

import com.example.mall.common.Constant;
import com.example.mall.exception.BusinessException;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.model.dao.CartMapper;
import com.example.mall.model.dao.ProductMapper;
import com.example.mall.model.pojo.Cart;
import com.example.mall.model.pojo.Product;
import com.example.mall.model.vo.CartVO;
import com.example.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.SELECTED);
            cartMapper.insertSelective(cart);
        } else {
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new BusinessException(ExceptionEnum.NEED_PRODUCT);
        }

        if (count>product.getStock()){
            throw new BusinessException(ExceptionEnum.LACK_PRODUCT);
        }

    }

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> list = cartMapper.selectList(userId);
        for (CartVO cartVO:list){
            cartVO.setTotalPrice(cartVO.getPrice()*cartVO.getQuantity());
        }
        return list;
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);

        if (cart==null) {
            throw new BusinessException(ExceptionEnum.UPDATE_FAILED);
        } else {
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }
    @Override
    public List<CartVO> delete(Integer userId, Integer productId){

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null){
            throw new BusinessException(ExceptionEnum.DELETE_FAILED);
        } else {
            int count = cartMapper.deleteByPrimaryKey(cart.getId());
            if (count==0){
                throw new BusinessException(ExceptionEnum.DELETE_FAILED);
            }
        }
        return list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null){
            throw new BusinessException(ExceptionEnum.UPDATE_FAILED);
        } else {
            cartMapper.selectOrNot(userId,productId,selected);
        }
        return list(userId);
    }
    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected){
        cartMapper.selectOrNot(userId,null,selected);
        return list(userId);
    }

}
