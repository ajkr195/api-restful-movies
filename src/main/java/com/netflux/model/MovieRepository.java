package com.netflux.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	Movie findById(long id);
	
	/*@Query(	value = "(SELECT id, title, img_url_preview, media_type FROM MOVIE WHERE outstanding =  1 ORDER BY id desc LIMIT 3) "
						+ "UNION "
						+ "(SELECT id, title, img_url_preview, media_type FROM SERIE WHERE outstanding = 1 ORDER BY id desc LIMIT 2);", 
			nativeQuery = true)*/
	
	@Query(	value = "SELECT * FROM ("
			+ "(SELECT id, title, img_url_preview, media_type FROM MOVIE WHERE outstanding =  1 ORDER BY id desc LIMIT 3) "
			+ "UNION "
			+ "(SELECT id, title, img_url_preview, media_type FROM SERIE WHERE outstanding = 1 ORDER BY id desc LIMIT 2))"
			+ "ORDER BY id DESC;", 
			nativeQuery = true)
	public Iterable<Object> getOutstanding();
	
	//useful in postcontroller.java for get last inserted id 
	public Movie findTopByOrderByIdDesc();
	
	public default void insertMovie(Movie movie) {
        save(movie);
    }
	
	@Query(	value = "SELECT * FROM MOVIE ORDER BY id DESC LIMIT 8;", nativeQuery = true)
	public Iterable<Movie> findTop8ByIdDesc();
	
	@Modifying
	@Transactional
	@Query(	value = "DELETE FROM movie_actor WHERE id_movie = :id_movie", nativeQuery = true)
	public void deleteFromMovieActorBySerieId (@Param("id_movie") long id_movie);
	
	@Query(	value = "SELECT * FROM MOVIE ORDER BY id DESC;", nativeQuery = true)
	public Iterable<Movie> findAllByDesc();
	

}
