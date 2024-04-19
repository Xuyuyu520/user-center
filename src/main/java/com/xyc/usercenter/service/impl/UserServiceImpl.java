package com.xyc.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyc.usercenter.model.domain.User;
import com.xyc.usercenter.service.UserService;
import com.xyc.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.xyc.usercenter.contant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author 27178
 * @description 用户服务实现类
 * @createDate 2024-04-17 20:57:09
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;

	/**
	 * 盐值 混淆加密
	 */
	private static final String SALT = "xuyc";
	/**
	 * key 鉴权
	 */

	/**
	 * 用户注册
	 *
	 * @param userAccount   用户账户
	 * @param userPassword  用户密码
	 * @param checkPassword 校验密码
	 * @return 用户id
	 */
	@Override
	public long userRegister(String userAccount, String userPassword, String checkPassword) {
		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			// todo 修改自定义异常
			return -1;
		}
		// 账户 不小于4位
		if (userAccount.length() < 4) {
			return -1;
		}
		// 密码不小于8位
		if (userPassword.length() < 8 || checkPassword.length() < 8) {
			return -1;
		}

		// 账户本能含有特殊字符
		if (!userAccount.matches("^[a-zA-Z0-9_]*$")) {
			return -1; // 用户账户含有特殊字符，注册失败
		}
		// 如果不相等
		if (!userPassword.equals(checkPassword)) {
			return -1;
		}
		// 账户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount", userAccount);
		long count = userMapper.selectCount(queryWrapper);
		if (count > 0) {
			return -1;
		}
		// 2.加密

		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
		// 3，插入数据
		User user = new User();
		user.setUserAccount(userAccount);
		user.setUserPassword(encryptPassword);
		boolean result = this.save(user);
		if (!result) {
			return -1;
		}
		return user.getId();
	}

	/**
	 * 用户登录
	 *
	 * @param userAccount  用户账户
	 * @param userPassword 用户密码
	 * @return 用户
	 */
	@Override
	public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			return null;
		}
		// 账户 不小于4位
		if (userAccount.length() < 4) {
			return null;
		}
		// 密码不小于8位
		if (userPassword.length() < 8) {
			return null;
		}

		// 账户本能含有特殊字符
		if (!userAccount.matches("^[a-zA-Z0-9_]*$")) {
			return null; // 用户账户含有特殊字符，注册失败
		}

		// 2.加密

		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
		// 账户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount", userAccount);
		queryWrapper.eq("userPassword", encryptPassword);
		User user = userMapper.selectOne(queryWrapper);
		if (user == null) {
			log.error("The user logged ,userAccount userPassword was incorrect");
			return null;
		}
		// 3 用户脱敏
		User safetyUser = getSafetyUser(user);
		// 4.记录用户登录状态
		request.getSession().setAttribute(USER_LOGIN_STATUS, user);
		return safetyUser;
	}

	/**
	 * 用户脱敏
	 *
	 * @param originUser
	 * @return
	 */
	@Override
	public User getSafetyUser(User originUser) {
		User safetyUser = new User();
		safetyUser.setId(originUser.getId());
		safetyUser.setUsername(originUser.getUsername());
		safetyUser.setUserAccount(originUser.getUserAccount());
		safetyUser.setAvatarUrl(originUser.getAvatarUrl());
		safetyUser.setGender(originUser.getGender());
		safetyUser.setPhone(originUser.getPhone());
		safetyUser.setEmail(originUser.getEmail());
		safetyUser.setUserStatus(originUser.getUserStatus());
		safetyUser.setCreateTime(originUser.getCreateTime());
		safetyUser.setUserRole(originUser.getUserRole());
		return safetyUser;
	}
}




