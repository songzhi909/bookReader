package com.songzhi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.songzhi.model.Catalog;

public interface CatalogDao extends JpaRepository<Catalog, Integer> {

	public Catalog findByName(String name);
	
	public List<Catalog> findByLevel(int level);
	
	public List<Catalog> findByPidOrderByIdAsc(int pid);
	
	public List<Catalog> findByClassTypeOrderByIdAsc(int classType);
	
}
