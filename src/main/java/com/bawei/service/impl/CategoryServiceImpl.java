package com.bawei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bawei.domain.Category;
import com.bawei.mapper.CategoryMapper;
import com.bawei.service.CategoryService;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月15日 下午8:15:08 
* 类功能说明 
*/
@Service
public class CategoryServiceImpl implements CategoryService {
	@Resource
	private CategoryMapper categoryMapper;

	@Override
	public List<Category> categorys(Integer channelId) {
		return categoryMapper.selects(channelId);
	} 
}
