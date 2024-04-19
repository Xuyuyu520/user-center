package com.xyc.usercenter.service;

import com.xyc.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 27178
 * @description 用户服务接口
 * @createDate 2024-04-17 20:57:09
 */
public interface UserService extends IService<User> {


	/**
	 * 用户注册
	 *
	 * @param userAccount   用户账户
	 * @param userPassword  用户密码
	 * @param checkPassword 校验密码
	 * @return 用户id
	 */
	long userRegister(String userAccount, String userPassword, String checkPassword);

	/**
	 * 用户登录
	 * @param userAccount 用户账户
	 * @param userPassword 用户密码
	 * @return 用户
	 */
	User userLogin(String userAccount, String userPassword, HttpServletRequest request);

	/**
	 * 用户脱敏
	 * @param originUser
	 * @return
	 */
	User getSafetyUser(User originUser);
}
