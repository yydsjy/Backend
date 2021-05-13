package com.example.mall.service.impl;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.common.Constant;
import com.example.mall.exception.BusinessException;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.model.dao.ProductMapper;
import com.example.mall.model.pojo.Product;
import com.example.mall.model.query.ProductListQuery;
import com.example.mall.model.request.AddProductReq;
import com.example.mall.model.request.ProductListReq;
import com.example.mall.model.request.UpdateProductReq;
import com.example.mall.model.vo.CategoryVO;
import com.example.mall.service.CategoryService;
import com.example.mall.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        Product rawProduct = productMapper.selectByName(addProductReq.getName());
        if (rawProduct!=null){
            throw new BusinessException(ExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new BusinessException(ExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq,product);
        Product rawProduct = productMapper.selectByName(product.getName());
        if (rawProduct!=null&&!rawProduct.getId().equals(product.getId())){
            throw new BusinessException(ExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count == 0) {
            throw new BusinessException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new BusinessException(ExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus){
        productMapper.batchUpdateSellStatus(ids,sellStatus);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    @Override
    public Product detail(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Override
    public PageInfo listForCustomer(ProductListReq productListReq){
        ProductListQuery productListQuery = new ProductListQuery();
        if (!StringUtils.isEmpty(productListReq.getKeyword())){
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }
        if (productListReq.getCategoryId()!=null){
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            List<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(categoryVOList,categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_AS_DESC.contains(orderBy)) {
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize(),orderBy);
        } else {
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize());
        }

        List<Product> products = productMapper.selectListForCustomer(productListQuery);
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    private void getCategoryIds(List<CategoryVO> categoryVOList,List<Integer> categoryIds){
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null){
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildCategory(),categoryIds);
            }
        }
    }
}
