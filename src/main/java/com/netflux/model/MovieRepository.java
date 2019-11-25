package com.netflux.model;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	Movie findById(long id);
}
