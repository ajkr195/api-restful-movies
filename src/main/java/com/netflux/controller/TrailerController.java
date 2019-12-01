package com.netflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflux.model.Trailer;
import com.netflux.model.TrailerRepository;
import com.netflux.model.TrailerQuery;

@CrossOrigin(origins = "*")
@RestController
public class TrailerController implements TrailerQuery{
	@Autowired
	private TrailerRepository trailerRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="/api/trailers")
	@ResponseBody
	@Override
	public Iterable<Trailer> getTopThree() {
		//return movieRepository.findAll();
		Iterable<Trailer> movie_list = trailerRepository.getTopThree();
		return movie_list;
	}
}
