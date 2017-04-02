package com.songzhi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.songzhi.model.Comic;

public interface ComicDao extends JpaRepository<Comic, Integer> {

	public Comic findByName(String name);
}
