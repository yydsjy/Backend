package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.filter.UserFilter;
import com.example.mall.model.vo.CartVO;
import com.example.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @ApiOperation("frontend add product to cart")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count){

        List<CartVO> list = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("frontend list product")
    @GetMapping("/list")
    public ApiRestResponse list(){

        List<CartVO> list = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(list);
    }

    @ApiOperation("frontend update cart")
    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count){
        List<CartVO> list = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("frontend delete cart")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId){
        List<CartVO> list = cartService.delete(UserFilter.currentUser.getId(), productId);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("frontend select/unselect product in cart")
    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam Integer productId, @RequestParam Integer selected){
        List<CartVO> list = cartService.selectOrNot(UserFilter.currentUser.getId(), productId, selected);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("frontend select/unselect all product in cart")
    @PostMapping("/selectAll")
    public ApiRestResponse selectAll(@RequestParam Integer selected){
        List<CartVO> list = cartService.selectAllOrNot(UserFilter.currentUser.getId(),selected);
        return ApiRestResponse.success(list);
    }


}
