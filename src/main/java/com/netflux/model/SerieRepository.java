package com.netflux.model;

import org.springframework.data.repository.CrudRepository;

public interface SerieRepository extends CrudRepository<Serie, Long>{
	Serie findById(long id);
}
