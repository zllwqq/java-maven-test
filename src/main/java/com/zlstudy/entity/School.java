package com.zlstudy.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * School entity. 
 * @author zl
 */
@Entity
@Table(name = "school", catalog = "newsclient_hnxxt")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class School implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private String name;
	private String city;
	private String county;
	private String township;
	private Integer studentTotal;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public School() {
	}

	/** minimal constructor */
	public School(String type, String name, Integer studentTotal) {
		this.type = type;
		this.name = name;
		this.studentTotal = studentTotal;
	}

	/** full constructor */
	public School(String type, String name, String city, String county,
			String township, Integer studentTotal, Integer status,
			Timestamp createTime, Timestamp updateTime) {
		this.type = type;
		this.name = name;
		this.city = city;
		this.county = county;
		this.township = township;
		this.studentTotal = studentTotal;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type", nullable = false, length = 64)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "city", length = 64)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "county", length = 64)
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "township", length = 64)
	public String getTownship() {
		return this.township;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	@Column(name = "studentTotal", nullable = false)
	public Integer getStudentTotal() {
		return this.studentTotal;
	}

	public void setStudentTotal(Integer studentTotal) {
		this.studentTotal = studentTotal;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateTime", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}