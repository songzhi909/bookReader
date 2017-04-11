package com.songzhi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.songzhi.model.Comic;

public interface ComicDao extends JpaRepository<Comic, Integer> {

	public Comic findByName(String name);
	
	public Page<Comic> findByFlagOrderByIdAsc(int flag, Pageable pageable);
	
	public Page<Comic> findByCatalogId(int catalogId, Pageable pageable);
}
