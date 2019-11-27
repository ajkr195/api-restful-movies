package com.netflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflux.model.Serie;
import com.netflux.model.SerieRepository;

@CrossOrigin(origins = "*")
@RestController
public class SerieController {
	@Autowired
	private SerieRepository serieRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="api/series/")
	@ResponseBody
	public Iterable<Serie> getAllSeries() {
		Iterable<Serie> movie_list = serieRepository.findAll();
		return movie_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/series/{id}")
	@ResponseBody
	public Serie getSerie(@PathVariable("id") int id) {
		return serieRepository.findById(id);
	}
}
