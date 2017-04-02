package com.songzhi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 漫画信息
 * 
 * @author songzhi
 *
 */
@Entity
public class Comic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3734348721597517693L;

	@Id
	@GeneratedValue
	private int id;

	@Column(length = 200)
	private String website; // 网站域名
	
	@Column(length = 1)
	private int wid; // 该网站中的ID
	
	@Column(length = 200)
	private String name; // 漫画名

	@Column(length = 400)
	private String photo; // 漫画首页图片

	@Column(length = 400)
	private String url; // 漫画资源路径

	@Column(length = 1)
	private int flag; // 下载标识

	public Comic() {
		// TODO Auto-generated constructor stub
	}

	public Comic(String name, String photo, String url) {
		super();
		this.name = name;
		this.photo = photo;
		this.url = url;
	}
	

	public Comic(String website, int wid, String name, String photo, String url) {
		super();
		this.website = website;
		this.wid = wid;
		this.name = name;
		this.photo = photo;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	@Override
	public String toString() {
		return "Comic [id=" + id + ", website=" + website + ", wid=" + wid + ", name=" + name + ", photo=" + photo
		    + ", url=" + url + ", flag=" + flag + "]";
	}

}
