/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CategoryServiceImpl
 * Author:   Administrator
 * Date:     2018/5/24/024 16:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mmall.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2018/5/24/024
 * @since 1.0.0
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse insertCategory(String categoryName, Integer parentId){
        if (parentId==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount>0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCateGoryName(Integer categoryId, String categoryName) {
        if (categoryId==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount>0){
            return ServerResponse.createBySuccessMessage("更新品类成功");
        }
        return ServerResponse.createByErrorMessage("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> listChildrenParallelCategory(Integer categoryId) {
        if (categoryId == null){
            return ServerResponse.createByErrorMessage("获取品类参数错误");
        }
        List<Category> list = categoryMapper.listCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(list)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Integer>> listCategoryAndDeepChildrenCategory(Integer categoryId) {
        Set<Category> categorieSet = Sets.newHashSet();
        findChildCategory(categorieSet, categoryId);

        List<Integer> catetoryIdList = Lists.newArrayList();
        if (categorieSet != null){
            for (Category categoryItem : categorieSet){
                catetoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(catetoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        List<Category> categories = categoryMapper.listCategoryChildrenByParentId(categoryId);
        for (Category categoryItemn : categories){
            findChildCategory(categorySet, categoryItemn.getId());
        }
        return categorySet;
    }
}