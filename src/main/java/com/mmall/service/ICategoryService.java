package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ServerResponse insertCategory(String categoryName, Integer parentId);

    ServerResponse updateCateGoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> listChildrenParallelCategory(Integer categoryId);

    ServerResponse listCategoryAndDeepChildrenCategory(Integer categoryId);
}
