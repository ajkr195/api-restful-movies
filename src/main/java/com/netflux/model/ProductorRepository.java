package com.netflux.model;

import org.springframework.data.repository.CrudRepository;

public interface ProductorRepository extends CrudRepository<Productor, Long>{
	//useful in postcontroller.java for get last inserted id 
	public Productor findTopByOrderByIdDesc();
}
