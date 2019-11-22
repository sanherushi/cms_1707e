package com.bawei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bawei.domain.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<Category> selects(@Param("channelId")Integer channelId);
}