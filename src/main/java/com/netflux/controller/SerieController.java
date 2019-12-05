package com.netflux.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflux.exception.NotFoundException;
import com.netflux.model.Serie;
import com.netflux.model.SerieDTO;
import com.netflux.model.SerieRepository;

@CrossOrigin(origins = "*")
@RestController
public class SerieController {
	@Autowired
	private SerieRepository serieRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="api/series/news")
	@ResponseBody
	public Iterable<SerieDTO> getAllSeries() {
		Iterable<Serie> serie_list = serieRepository.findAll();
		ArrayList<SerieDTO> serieDTO_list = new ArrayList<SerieDTO>();
		for(Serie m : serie_list){
			serieDTO_list.add(new SerieDTO(m.getId(), m.getTitle(), m.getImg_url(), m.getMedia_type()));
		}
		return serieDTO_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/series/{id}")
	@ResponseBody
	public Serie getSerie(@PathVariable("id") int id) {
		Serie serie = null;
		serie = serieRepository.findById(id);
		if(serie == null) {
			throw new NotFoundException(); 
		}
		return serie;
	}
}
