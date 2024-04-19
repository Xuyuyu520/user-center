package com.xyc.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xuYuYu
 * @createTime: 2024/4/18 18:53
 * @Description: TODO  用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {
	private static final long serialVersionUID = 8137328264136150570L;

	private String userAccount, userPassword;
}
