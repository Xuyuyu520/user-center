package com.xyc.usercenter.service;

import java.util.Date;

import com.xyc.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author: xuYuYu
 * @createTime: 2024/4/17 21:03
 * @Description: TODO 用户服务测试
 */
@SpringBootTest
class UserServiceTest {
	@Resource
	private UserService userService;

	@Test
	void testAddUser() {
		User user = new User();
		user.setId(1L);
		user.setUsername("JohnDoe");
		user.setUserAccount("john@example.com");
		user.setAvatarUrl("https://example.com/avatar.jpg");
		user.setGender(1);
		user.setUserPassword("password123");
		user.setPhone("1234567890");
		user.setEmail("john@example.com");
		user.setUserStatus(0);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setIsDelete(0);

		userService.save(user);
	}

	@Test
	void userRegisterTest() {

		// 测试账户不小于4位
		String shortAccount = "abc";
		long shortAccountResult = userService.userRegister(shortAccount, "password123", "password123");
		assertEquals(-1, shortAccountResult); // 期望返回-1，表示注册失败

		// 测试密码不小于8位
		String shortPassword = "pass";
		long shortPasswordResult = userService.userRegister("testUser", shortPassword, shortPassword);
		assertEquals(-1, shortPasswordResult); // 期望返回-1，表示注册失败

		// 测试账户不能重复，这里需要根据具体的数据库状态进行测试

		// 测试账户不包含特殊字符
		String specialAccount = "testUser@";
		long specialAccountResult = userService.userRegister(specialAccount, "password123", "password123");
		assertEquals(-1, specialAccountResult); // 期望返回-1，表示注册失败

		// 测试密码和校验密码相同
		String mismatchPassword = "password123";
		long mismatchPasswordResult = userService.userRegister("testUser", "password123", mismatchPassword);
		assertEquals(-1, mismatchPasswordResult); // 期望返回-1，表示注册失败

		// 测试正常注册情况
		long normalResult = userService.userRegister("testUser", "password123", "password123");
		assertNotEquals(1, normalResult); // 期望返回非-1，表示注册成功
	}

}
