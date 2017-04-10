package com.songzhi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 目录
 * 
 * @author songzhi
 *
 */
@Entity
public class Catalog {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(length = 1)
	private Integer pid;

	@Column(length = 1)
	private Integer level;

	@Column(length = 1)
	private Integer classType; // 类型 1:小说, 2: 漫画:, 3: 漫画网站

	@Column(length = 1)
	private Integer subType; // 子分类 小说:1:玄幻小说, 2:修行小说, 3: 都市小说

	@Column(length = 200)
	private String name; // 名称

	@Column(length = 400)
	private String url; // url资源地址
	
	public Catalog() {
		// TODO Auto-generated constructor stub
	}

	public Catalog(Integer id, String name, Integer level) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClassType() {
		return classType;
	}

	public void setClassType(Integer classType) {
		this.classType = classType;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
//		return "Catalog [id=" + id + ", pid=" + pid + ", level=" + level + ", classType=" + classType + ", subType=" + subType + ", name=" + name + ", url=" + url + "]";
		return name;
	}

}
