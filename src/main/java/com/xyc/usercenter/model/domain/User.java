package com.xyc.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
	/**
	 * 用户ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 昵称
	 */
	private String username;

	/**
	 * 登录账号
	 */
	private String userAccount;

	/**
	 * 头像
	 */
	private String avatarUrl;

	/**
	 * 性别：0表示未知，1表示男性，2表示女性
	 */
	private Integer gender;

	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户状态：0表示正常，1表示其他状态
	 */
	private Integer userStatus;

	/**
	 * 创建时间 （数据插入时间）
	 */
	private Date createTime;

	/**
	 * 更新时间 （数据更新时间）
	 */
	private Date updateTime;

	/**
	 * 是否删除：0表示未删除，1表示已删除
	 */
	@TableLogic
	private Integer isDelete;

	/**
	 * 是否删除：0表示普通用户，1表示管理员
	 */
	private Integer userRole;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}
