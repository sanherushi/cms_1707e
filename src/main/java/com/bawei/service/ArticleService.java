package com.bawei.service;


import com.bawei.domain.Article;
import com.bawei.domain.ArticleWithBLOBs;
import com.github.pagehelper.PageInfo;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月14日 下午5:02:01 
* 类功能说明 
*/
public interface ArticleService {
	
	PageInfo<Article> selects(Article article, Integer pageNum, Integer pageSize);

	boolean update(ArticleWithBLOBs article);
	
	ArticleWithBLOBs selectByPrimaryKey(Integer id);

	boolean insertSelective(ArticleWithBLOBs article);

}
