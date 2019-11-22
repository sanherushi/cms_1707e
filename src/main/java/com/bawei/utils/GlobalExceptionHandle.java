package com.bawei.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月21日 下午7:32:13 
* 类功能说明 
*/
public class GlobalExceptionHandle {
	/**
	 * 处理ajax请求的异常
	 * @param cmsJsonException
	 * @return
	 */
	public Result handleJson(CMSAjaxException cmsJsonException) {
		return ResultUtil.error(cmsJsonException.getCode(),cmsJsonException.getMessage());
		
	}
	/**
	 * 处理普通请求的异常
	 *
	 */
	public ModelAndView handle(CMSException exception,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		//获取错误消息,进行封装
		mv.addObject("message", exception.getMessage());
		//获取当前请求的url
		String url = request.getRequestURI();
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 处理系统异常
	 */
	
	public String handle(Exception exception) {
		return "common/error";
	}
}
