package com.example.mall.service.impl;

import com.example.mall.exception.BusinessException;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.model.dao.CategoryMapper;
import com.example.mall.model.pojo.Category;
import com.example.mall.model.request.AddCategoryReq;
import com.example.mall.model.request.UpdateCategoryReq;
import com.example.mall.model.vo.CategoryVO;
import com.example.mall.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category rawCategory = categoryMapper.selectByName(addCategoryReq.getName());
        if (rawCategory!=null){
            throw new BusinessException(ExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count==0){
            throw new BusinessException(ExceptionEnum.CREATE_FAILED);
        }

    }

    @Override
    public void update(UpdateCategoryReq updateCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryReq,category);

        if (category.getName()!=null){
            Category rawCategory = categoryMapper.selectByName(category.getName());
            if (rawCategory!= null && !rawCategory.getId().equals(category.getId())){
                throw new BusinessException(ExceptionEnum.NAME_EXISTED);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (count==0){
            throw new BusinessException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        Category rawCategory = categoryMapper.selectByPrimaryKey(id);
        if (rawCategory==null){
            throw new BusinessException(ExceptionEnum.DELETE_FAILED);
        }

        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count==0){
            throw new BusinessException(ExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(Integer parentId){
        List<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList,parentId);
        return categoryVOList;
    }

    public void recursivelyFindCategories(List<CategoryVO> categoryVOList,Integer id){
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(id);
        if (!CollectionUtils.isEmpty(categoryList)){
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }
    }
}
