package com.bawei.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bawei.domain.Article;
import com.bawei.domain.ArticleWithBLOBs;
import com.bawei.domain.User;
import com.bawei.service.ArticleService;
import com.bawei.service.UserService;
import com.github.pagehelper.PageInfo;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月13日 下午8:13:03 
* 类功能说明 
*/
@RequestMapping("admin")
@Controller
public class AdmainController {
	@Resource
	private UserService userService;
	@Resource
	private ArticleService articleService;
	
	@RequestMapping("index")
	public String index() {
		return "admin/index";
	}
	/**
	 * UserController的方法
	 * @param model
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("user/selects")
	public String list(Model model,User user,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<User> page = userService.selects(user, pageNum, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("user", user);
		int[] nums = page.getNavigatepageNums();
		model.addAttribute("nums", nums);
		return "admin/user/users";
		
	}
	@RequestMapping("user/update")
	@ResponseBody
	public boolean update(User user) {
		boolean flag = userService.update(user);
		return flag;
	}
	
	
	/**
	 * ArticleController方法
	 * @param article
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("article/articles")
	public String articles(Article article,Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<Article> page = articleService.selects(article,pageNum,pageSize);
		int[] nums = page.getNavigatepageNums();
		model.addAttribute("page",page);
		model.addAttribute("article", article);//用于查询条件的回显,便于管理员进行操作
		model.addAttribute("nums", nums);
		return "admin/article/articles";
	}
	
	@RequestMapping("article/update")
	@ResponseBody
	public boolean update(ArticleWithBLOBs article) {
		System.out.println(article.getHot()+"----"+article.getId());
		boolean flag = articleService.update(article);
		return flag;
	}
	@RequestMapping("article/article")
	public String detail(Article a,Model model,Integer pageNum) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(a.getId());
		model.addAttribute("article", article);
		//将原来的状态值封装到model中
		model.addAttribute("a", a);
		model.addAttribute("pageNum", pageNum);
		return "admin/article/article";
		
	}
}
