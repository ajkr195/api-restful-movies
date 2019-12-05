package com.netflux.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflux.model.MovieRepository;
import com.netflux.model.MovieDTO;
import com.netflux.model.Movie;
import com.netflux.model.OutstandingDTO;

import com.netflux.exception.NotFoundException;

@CrossOrigin(origins = "*")
@RestController
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="api/movies/news")
	@ResponseBody
	public Iterable<MovieDTO> getAllMovies() {
		Iterable<Movie> movie_list = movieRepository.findAll();
		ArrayList<MovieDTO> movieDTO_list = new ArrayList<MovieDTO>();
		for(Movie m : movie_list){
			movieDTO_list.add(new MovieDTO(m.getId(), m.getTitle(), m.getImg_url(), m.getMedia_type()));
		}
		return movieDTO_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/movies/{id}")
	@ResponseBody
	public Movie getMovie(@PathVariable("id") int id) {
		Movie movie = null;
		movie = movieRepository.findById(id);
		if(movie == null) {
			throw new NotFoundException(); 
		}
		return movie;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/outstanding")
	@ResponseBody
	public Iterable<OutstandingDTO> getOutstanding() {
		Iterable<Object> object_list = movieRepository.getOutstanding();
		ArrayList<OutstandingDTO> outstanding_list = new ArrayList<OutstandingDTO>();
		for(Object o : object_list){
			Object[] obj = (Object[])o;
			outstanding_list.add(new OutstandingDTO(((BigInteger) obj[0]).longValue(),(String)obj[1],(String)obj[2],(int)obj[3]));
		}
		return outstanding_list;
		
	}
}
