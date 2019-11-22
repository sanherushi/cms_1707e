package com.bawei.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bawei.domain.Article;
import com.bawei.domain.ArticleWithBLOBs;
import com.bawei.domain.User;
import com.bawei.service.ArticleService;
import com.github.pagehelper.PageInfo;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月15日 下午5:46:18 
* 类功能说明 
*/
@RequestMapping("my")
@Controller
public class MyController {
	@Resource
	private ArticleService  articleService;
	@RequestMapping(value = {"","/","index"})
	public String index() {
		return "my/index";
	}
	@GetMapping("publish")
	public String publish() {
		return "my/article/publish";
	}
	@GetMapping("selectByUser")
	public String selectByUser(HttpServletRequest request,Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		Article article = new Article();
		HttpSession session = request.getSession(false);
		if(null==session) {
			return "redirect:/passport/login";//重定向
		}
		User user = (User)session.getAttribute("user");
		article.setUserId(user.getId());
		PageInfo<Article> info = articleService.selects(article,pageNum,pageSize);
		List<Article> list = info.getList();
		for (Article a : list) {
			System.out.println(a.getUserId()+"----------------");
		}
		int[] nums = info.getNavigatepageNums();
		model.addAttribute("nums", nums);
		model.addAttribute("page", info);
		return "my/article/articles";//个人中心的文章
	}
	@RequestMapping("article")
	public String detail(Integer id,Model model,Integer pageNum) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		model.addAttribute("article", article);
		model.addAttribute("pageNum", pageNum);
		return "my/article/article";
		
	}
	@PostMapping("publish")
	@ResponseBody
	public boolean publish(HttpServletRequest request,ArticleWithBLOBs article,MultipartFile file) {
		if(!file.isEmpty()) {
			//文件上传路径,把文件放到项目的/resource/pic
			String path = request.getSession().getServletContext().getRealPath("/resource/pic/");
			//获取上传文件的源名称
			String oldFilename = file.getOriginalFilename();
			//防止文件重名,使用UUID的方式重命名上传的文件
			String newFilename = UUID.randomUUID()+oldFilename.substring(oldFilename.lastIndexOf("."));
			File f = new File(path,newFilename);
			//写入硬盘
			try {
				file.transferTo(f);//将file文件的内容上传到指定的file文件中
				article.setPicture(newFilename);//标题图片
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//初始化设置
		article.setStatus(0);//0,刚发布等待审核,1审核通过,-1 审核未通过
		HttpSession session = request.getSession(false);
		if(null==session) {
			return false;
		}
		User user = (User)session.getAttribute("user");
		article.setUserId(user.getId());//设置对应的用户Id值
		article.setHits(0);//点击率设置为0
		article.setHot(0);//0表示不是热门文章
		article.setDeleted(0);//0:正常,1:删除
		article.setCreated(new Date());//创建时间初始化为当前时间
		article.setUpdated(new Date());//修改时间初始化为当前时间
		return articleService.insertSelective(article);
		
	}
	
}
