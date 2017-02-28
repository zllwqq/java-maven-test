package com.zlstudy.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * User entity. @author 
 * UniqueConstraint 唯一约束，对应mysql数据库唯一性索引
 */
@Entity
@Table(name = "user", catalog = "newsclient_hnxxt", uniqueConstraints = @UniqueConstraint(columnNames = {
		"schoolId", "userId" }))
public class User implements java.io.Serializable {

	// Fields

	private Long userId;
	private String username;
	private String name;
	private String password;
	private String mobile;
	private Integer schoolId;
	private String orderType;
	private String role;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Date loginTime;
	private Date expireTime;
	
	private String schoolName;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String name, String password, String mobile,
			Integer schoolId, String orderType, String role, Integer status,
			Timestamp createTime, Timestamp updateTime, Timestamp loginTime,
			Date expireTime) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.mobile = mobile;
		this.schoolId = schoolId;
		this.orderType = orderType;
		this.role = role;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.loginTime = loginTime;
		this.expireTime = expireTime;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userId", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "username", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "name", length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", length = 24)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "mobile", length = 24)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "schoolId")
	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name = "orderType", length = 2)
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "role", length = 2)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateTime", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "loginTime", length = 19)
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expireTime", length = 10)
	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Transient
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}