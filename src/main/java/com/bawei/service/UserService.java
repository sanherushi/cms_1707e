package com.bawei.service;

import com.bawei.domain.User;
import com.bawei.vo.UserVO;
import com.github.pagehelper.PageInfo;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月13日 下午5:56:53 
* 类功能说明 
*/
public interface UserService {
	PageInfo<User> selects(User user,Integer pageNum,Integer pageSize);
	
	boolean update(User user);

	boolean insertSelective(UserVO userVO);

	User login(User user);
}
