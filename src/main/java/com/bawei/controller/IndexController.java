package com.bawei.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bawei.domain.Article;
import com.bawei.domain.ArticleWithBLOBs;
import com.bawei.domain.Category;
import com.bawei.domain.Channel;
import com.bawei.service.ArticleService;
import com.bawei.service.CategoryService;
import com.bawei.service.ChannelService;
import com.github.pagehelper.PageInfo;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月20日 下午1:40:12 
* 类功能说明 
*/
@Controller
public class IndexController {
	@Resource
	private ChannelService channelService;//栏目
	@Resource
	private ArticleService articleService;//文章
	@Resource
	private CategoryService categoryService;//分类
	@RequestMapping(value = {"","/","index"})
	public String index(Article article,Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		System.out.println(article.getChannelId()+"---------------");
		
		article.setStatus(1);//必须是审核通过的文章
		article.setDeleted(0);//必须是没有被删除的文章
		//查询出左侧的栏目
		List<Channel> channels = channelService.selects();
		//用于显示左侧的栏目
		model.addAttribute("channels", channels);
		//如果没有栏目,默认选择热门文章
		if(null ==article.getChannelId()) {
			Article hot = new Article();
			hot.setStatus(1);//1表示审核过
			hot.setDeleted(0);//0表示没有被删除
			hot.setHot(1);//1表示是热门文章
			//查询出全部的热门文章
			PageInfo<Article> info = articleService.selects(hot, pageNum, pageSize);
			model.addAttribute("nums", info.getNavigatepageNums());
			model.addAttribute("page", info);
		}
		if(null !=article.getChannelId()) {
			//通过栏目Id获取栏目下所有的分类
			List<Category> categorys = categoryService.categorys(article.getChannelId());
			model.addAttribute("categorys", categorys);
			//显示分类下的文章
			PageInfo<Article> info = articleService.selects(article, pageNum, pageSize);
			model.addAttribute("nums", info.getNavigatepageNums());
			model.addAttribute("page", info);
		}
		//右侧边栏显示最新的5片文章
		Article lastArticle = new Article();
		lastArticle.setStatus(1);
		lastArticle.setDeleted(0);
		//把当前页和每页的记录数先写死,保证不管怎么分页最新的5篇文章都不会改变
		PageInfo<Article> lastInfo = articleService.selects(lastArticle, 1, 5);
		model.addAttribute("lastInfo", lastInfo);
		
		//封装查询条件
		model.addAttribute("article", article);
		model.addAttribute("categoryId", article.getCategoryId());
		return "index/index";
		
	}
	
	@RequestMapping("article")
	public String article(Model model,Integer id) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		model.addAttribute("article", article);
		return "index/article";
		
	}
	
}
