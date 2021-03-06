package com.netflux.model;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailerRepository extends CrudRepository<Trailer, Long> {
	Trailer findById(long id);
	
	public Trailer findTopByOrderByIdDesc();
	
	@Query(value = "SELECT * FROM trailer ORDER BY id DESC LIMIT 3", nativeQuery = true)
	public Iterable<Trailer> getTopThree();
	
	@Query(value = "SELECT * FROM trailer ORDER BY id DESC", nativeQuery = true)
	public Iterable<Trailer> findAllByDesc();
}
