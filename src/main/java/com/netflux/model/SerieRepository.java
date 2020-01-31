package com.netflux.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SerieRepository extends CrudRepository<Serie, Long>{
	Serie findById(long id);
	
	//useful in postcontroller.java for get last inserted id 
	public Serie findTopByOrderByIdDesc();
	
	public default void insertSerie(Serie serie) {
        save(serie);
    }
	
	@Query(	value = "SELECT * FROM SERIE ORDER BY id DESC LIMIT 8;", nativeQuery = true)
	public Iterable<Serie> findTop8ByIdDesc();
	
	@Modifying
	@Transactional
	@Query(	value = "DELETE FROM serie_actor WHERE id_serie = :id_serie", nativeQuery = true)
	public void deleteFromSerieActorBySerieId (@Param("id_serie") long id_serie);
	
	@Modifying
	@Transactional
	@Query(	value = "DELETE FROM serie_productor WHERE id_serie = :id_serie", nativeQuery = true)
	public void deleteFromSerieProductorBySerieId (@Param("id_serie") long id_serie);
	
	@Query(	value = "SELECT * FROM SERIE ORDER BY id DESC;", nativeQuery = true)
	public Iterable<Serie> findAllByDesc();
}
