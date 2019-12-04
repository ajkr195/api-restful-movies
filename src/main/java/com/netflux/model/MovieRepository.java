package com.netflux.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	Movie findById(long id);
	
	@Query(	value = "(SELECT id, title, img_url_preview, media_type FROM MOVIE WHERE outstanding =  1 ORDER BY id desc LIMIT 3) "
						+ "UNION "
						+ "(SELECT id, title, img_url_preview, media_type FROM SERIE WHERE outstanding = 1 ORDER BY id desc LIMIT 2);", 
			nativeQuery = true)
	public Iterable<Object> getOutstanding();
}
