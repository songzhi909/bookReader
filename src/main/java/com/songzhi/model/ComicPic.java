package com.songzhi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 漫画图片
 * @author songzhi
 *
 */
@Entity
public class ComicPic {

	@Id @GeneratedValue
	private int id;
	
	@Column
	private int comicId; //漫画ID
	
	@Column(length=400)
	private String picUrl;  //图片url地址
	
	@Column(length=200)
	private String filepath; //文件路径
	
	@Column(length=1)
	private int flag; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getComicId() {
		return comicId;
	}

	public void setComicId(int comicId) {
		this.comicId = comicId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Override
	public String toString() {
		return "ComicPic [id=" + id + ", comicId=" + comicId + ", picUrl=" + picUrl + ", filepath=" + filepath + "]";
	}

	public ComicPic(int id, int comicId, String picUrl, String filepath) {
		super();
		this.comicId = comicId;
		this.picUrl = picUrl;
	}
	
	public ComicPic(int comicId, String picUrl) {
		super();
		this.comicId = comicId;
		this.picUrl = picUrl;
	}

	public ComicPic() {
		// TODO Auto-generated constructor stub
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
