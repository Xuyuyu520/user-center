package com.xyc.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyc.usercenter.model.domain.User;
import com.xyc.usercenter.model.domain.request.UserLoginRequest;
import com.xyc.usercenter.model.domain.request.UserRegisterRequest;
import com.xyc.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.xyc.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.xyc.usercenter.contant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author: xuYuYu
 * @createTime: 2024/4/18 18:41
 * @Description: TODO 服务控制层
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/register")
	public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
		if (userRegisterRequest == null) {
			return null;
		}
		String userAccount = userRegisterRequest.getUserAccount();
		String userPassword = userRegisterRequest.getUserPassword();
		String checkPassword = userRegisterRequest.getCheckPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			return null;
		}
		long id = userService.userRegister(userAccount, userPassword, checkPassword);
		return id;
	}

	@PostMapping("/login")
	public User userLogin(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
		if (loginRequest == null) {
			return null;
		}
		String userAccount = loginRequest.getUserAccount();
		String userPassword = loginRequest.getUserPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			return null;
		}
		return userService.userLogin(userAccount, userPassword, request);
	}

	@GetMapping("/search")
	public List<User> searchUser(String username, HttpServletRequest request) {
		if (!isAdmin(request)) {
			return new ArrayList<>();
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(username)) {
			queryWrapper.like("username", username);
		}
		List<User> userList = userService.list();
		return userList.stream().map(userService::getSafetyUser).collect(Collectors.toList());
	}

	@PostMapping("/add")
	public boolean addUser(@RequestBody User user, HttpServletRequest request) {
		if (!isAdmin(request)) {
			return false;
		}
		if (user == null) {
			return false;
		}
		return userService.save(user);
	}

	@PutMapping("/update")
	public boolean updateUser(@RequestBody User user, HttpServletRequest request) {
		if (!isAdmin(request)) {
			return false;
		}
		return userService.updateById(user);
	}

	@PostMapping("/delete")
	public boolean deleteUser(@RequestBody Long id, HttpServletRequest request) {
		if (!isAdmin(request)) {
			return false;
		}
		if (id == null) {
			return false;
		}
		return userService.removeById(id);
	}

	/**
	 * 是否是管理员
	 *
	 * @param request
	 * @return
	 */
	private boolean isAdmin(HttpServletRequest request) {
		// 1,鉴权 管理员权限
		Object attribute = request.getSession().getAttribute(USER_LOGIN_STATUS);
		User userObj = (User)  attribute;
		return userObj != null || userObj.getUserRole() == ADMIN_ROLE;
	}
}

