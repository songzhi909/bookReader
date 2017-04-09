package com.songzhi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.songzhi.model.ComicPic;

public interface ComicPicDao extends JpaRepository<ComicPic, Integer> {

	public List<ComicPic> findByComicIdAndPicUrl(int comicId, String picUrl);
	
	public List<ComicPic> findByComicId(int comicId);
}
