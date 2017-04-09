package com.songzhi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.songzhi.dao.CatalogDao;
import com.songzhi.dao.ComicDao;
import com.songzhi.dao.ComicPicDao;
import com.songzhi.model.Catalog;
import com.songzhi.model.Comic;
import com.songzhi.model.ComicPic;

@Service
@Transactional
public class ComicService {
	final static Logger log = LoggerFactory.getLogger(ComicService.class);
	
	@Autowired
	private ComicDao comicDao;
	
	@Autowired
	private ComicPicDao comicPicDao;
	
	@Autowired
	private CatalogDao catalogDao;

	public String add(Comic comic) {
		comicDao.save(comic);
		return "添加成功！";
	}
	
	public Page<Comic> findComicsByPage(int page, int size) {
		return comicDao.findByFlagOrderByIdAsc(0, new PageRequest(page, size));
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

	/**是否存在该comicpic信息*/
	public boolean exsits(ComicPic comicPic) {
		List<?> list = this.comicPicDao.findByComicIdAndPicUrl(comicPic.getComicId(), comicPic.getPicUrl());
		if(list != null && list.size() > 0) return true;
		return false;
	}
	
	public boolean deleteComicPics(Comic comic) {
		boolean flag = false;
		try {
			List<ComicPic> comicPics = this.comicPicDao.findByComicId(comic.getId());
			this.comicPicDao.delete(comicPics);
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 查询漫画的图片资源路径
	 * @return
	 */
	public List<ComicPic> findComicPicUrls(Comic comic) {
		List<ComicPic> pics = this.comicPicDao.findByComicId(comic.getId());
		return pics;
	}

	public boolean update(Comic comic) {
		boolean flag = false;
		try {
			this.comicDao.saveAndFlush(comic);
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}
	
	public List<Catalog> findTopCatalogs() {
		List<Catalog> catalogs = this.catalogDao.findByLevel(1);
		return catalogs;
	}

	public List<Catalog> findCatalogsByPid(int pid) {
		List<Catalog> catalogs = this.catalogDao.findByPidOrderByIdAsc(pid);
		return catalogs;
	}
	public List<Catalog> findAllCatalogs() {
		List<Catalog> catalogs = this.catalogDao.findAll(new Sort(Direction.ASC, "id"));
		return catalogs;
	}
}
