package com.songzhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.songzhi.dao.ComicDao;
import com.songzhi.dao.ComicPicDao;
import com.songzhi.model.Comic;
import com.songzhi.model.ComicPic;

@Service
@Transactional
public class ComicService {

	@Autowired
	private ComicDao comicDao;
	
	@Autowired
	private ComicPicDao comicPicDao;

	public String add(Comic comic) {
		comicDao.save(comic);
		return "添加成功！";
	}
	
	public String addPic(ComicPic comicPic) {
		comicPicDao.save(comicPic);
		return "添加成功！";
	}
	
	public Comic findById(int id) {
		return this.comicDao.findOne(id);
	}
	
	public Comic findByName(String name) {
		return this.comicDao.findByName(name);
	}

	public List<Comic> findAll() {
		return this.comicDao.findAll();
	}
	
	public Comic getOneComic(int id) { 
		return comicDao.findOne(id);
	}
}
