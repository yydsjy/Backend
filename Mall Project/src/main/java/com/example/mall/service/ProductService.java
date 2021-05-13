package com.example.mall.service;

import com.example.mall.model.pojo.Product;
import com.example.mall.model.request.AddProductReq;
import com.example.mall.model.request.ProductListReq;
import com.example.mall.model.request.UpdateProductReq;
import com.github.pagehelper.PageInfo;

public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(UpdateProductReq updateProductReq);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo listForCustomer(ProductListReq productListReq);
}
