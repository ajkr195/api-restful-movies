package com.netflux.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ActorRepository extends CrudRepository<Actor, Long>{

	public Actor findTopByOrderByIdDesc();
	
	@Query(	value = "SELECT * FROM ACTOR ORDER BY id DESC;", nativeQuery = true)
	public Iterable<Actor> findAllByDesc();
	
	@Modifying
	@Transactional
	@Query(	value = "DELETE FROM movie_actor WHERE id_actor = :id_actor", nativeQuery = true)
	public void deleteFromMovieActorByActorId (@Param("id_actor") long id_actor);
	
	@Modifying
	@Transactional
	@Query(	value = "DELETE FROM serie_actor WHERE id_actor = :id_actor", nativeQuery = true)
	public void deleteFromSerieActorByActorId (@Param("id_actor") long id_actor);
}
