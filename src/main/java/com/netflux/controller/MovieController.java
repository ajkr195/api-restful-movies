package com.netflux.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflux.model.*;

@RestController
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="/movie")
	@ResponseBody
	public Iterable<Movie> getAllMovies() {
		return movieRepository.findAll();
		/*Iterable<Movie> collection = movieRepository.findAll();
		for(Movie m : collection){
		    System.out.println(m.toString());
		}
		return collection;*/
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/movie/{id}")
	@ResponseBody
	public Movie getMovie(@PathVariable("id") int id) {
		return movieRepository.findById(id);
	}
}
