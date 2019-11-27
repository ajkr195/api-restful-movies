package com.netflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflux.model.*;

@CrossOrigin(origins = "*")
@RestController
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="/movies")
	@ResponseBody
	public Iterable<Movie> getAllMovies() {
		//return movieRepository.findAll();
		Iterable<Movie> movie_list = movieRepository.findAll();
		/*for(Movie m : movie_list){
		    System.out.println(m.toString());
		    List<Actor> casting = m.getCasting();
		    for (Actor a : casting) {
		    	System.out.println(a.toString());
		    }
		}*/
		return movie_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/movies/{id}")
	@ResponseBody
	public Movie getMovie(@PathVariable("id") int id) {
		return movieRepository.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/news")
	@ResponseBody
	public Movie getNewMovies(@PathVariable("id") int id) {
		return movieRepository.findById(id);
	}
}
