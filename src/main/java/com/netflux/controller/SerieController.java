package com.netflux.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflux.exception.NotFoundException;
import com.netflux.model.Reply;
import com.netflux.model.Actor;
import com.netflux.model.ActorRepository;
import com.netflux.model.Productor;
import com.netflux.model.ProductorRepository;
import com.netflux.model.Serie;
import com.netflux.model.SerieDTO;
import com.netflux.model.SerieRepository;
import com.netflux.service.FileUploadService;

@CrossOrigin(origins = "*")
@RestController
public class SerieController {
	@Autowired
	private SerieRepository serieRepository;
	@Autowired 
	private FileUploadService fileUploadService;
	@Autowired
	private ProductorRepository productorRepository;
	@Autowired 
	private ActorRepository actorRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="api/series/news")
	@ResponseBody
	public Iterable<SerieDTO> getLast8Series() {
		Iterable<Serie> serie_list = serieRepository.findTop8ByIdDesc();
		ArrayList<SerieDTO> serieDTO_list = new ArrayList<SerieDTO>();
		for(Serie m : serie_list){
			serieDTO_list.add(new SerieDTO(m.getId(), m.getTitle(), m.getImg_url(), m.getMedia_type()));
		}
		return serieDTO_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/series/all")
	@ResponseBody
	public Iterable<SerieDTO> getAllSeries() {
		Iterable<Serie> serie_list = serieRepository.findAllByDesc();
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
	
	@RequestMapping(method = RequestMethod.POST, value="api/series/post")
	@ResponseBody
	public Reply postSerie(
			@RequestParam("mediaData") String mediaData, 
			@RequestParam("inputCover") MultipartFile imgCover, 
			@RequestParam("inputPreview") MultipartFile imgPreview,
			@RequestParam("coverName") String coverName, 
			@RequestParam("previewName") String previewName,
			@RequestParam("creators") String creators,
			@RequestParam("actorList") String actorList	
			) throws IllegalStateException, IOException {
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Serie serie = mapper.readValue(mediaData, Serie.class);
		System.out.println(serie.toString());
		//getting last serie and setting id to new serie
		Serie lastSerie = serieRepository.findTopByOrderByIdDesc();
		serie.setId(lastSerie.getId()+1);
		
		
		//getting last Productor and setting serie's creators 
		if(creators.length()>0) {
			Productor productor = productorRepository.findTopByOrderByIdDesc();
			List<Productor> creatorsList = new ArrayList<>();
			String[] creatorArray = creators.split(",");
			Long productorId = productor.getId() + 1;
			for(int i = 0; i<creatorArray.length; i++) {
				creatorsList.add(new Productor(productorId++,creatorArray[i]));
			}		
			serie.setCreators(creatorsList);
		}
		//getting casting list
		if(actorList.length()>0) {
			List<Actor> casting = new ArrayList<>();
			String[] actorsArray = actorList.split(",");
			for(int i = 0; i<actorsArray.length; i++) {
				casting.add(actorRepository.findById(Long.parseLong(actorsArray[i])).get());
			}
			serie.setCasting(casting);			
		}
		//inserting new serie
		serieRepository.insertSerie(serie);
		
		//Copying images
		fileUploadService.fileUpload(imgCover, coverName);
		fileUploadService.fileUpload(imgPreview, previewName);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="api/series/edit")
	@ResponseBody
	public Reply editSerie(
			@RequestParam("mediaData") String mediaData, 
			@RequestParam(value = "inputCover", required = false) MultipartFile imgCover, 
			@RequestParam(value = "inputPreview", required = false) MultipartFile imgPreview,
			@RequestParam("coverName") String coverName, 
			@RequestParam("previewName") String previewName,
			@RequestParam("creators") String creators,
			@RequestParam("actorList") String actorList	
			) throws IllegalStateException, IOException {
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Serie serie = mapper.readValue(mediaData, Serie.class);
		
		//getting last Productor and setting serie's creators 
		if(creators.length()>0) {
			Productor productor = productorRepository.findTopByOrderByIdDesc();
			List<Productor> creatorsList = new ArrayList<>();
			String[] creatorArray = creators.split(",");
			Long productorId = productor.getId() + 1;
			for(int i = 0; i<creatorArray.length; i++) {
				creatorsList.add(new Productor(productorId++,creatorArray[i]));
			}		
			serie.setCreators(creatorsList);
		}
		//getting casting list
		if(actorList.length()>0) {
			List<Actor> casting = new ArrayList<>();
			String[] actorsArray = actorList.split(",");
			for(int i = 0; i<actorsArray.length; i++) {
				casting.add(actorRepository.findById(Long.parseLong(actorsArray[i])).get());
			}
			serie.setCasting(casting);			
		}
		//inserting new serie
		serieRepository.insertSerie(serie);
		
		//Copying images
		if(imgCover != null)
			fileUploadService.fileUpload(imgCover, coverName);
		if(imgPreview != null)
			fileUploadService.fileUpload(imgPreview, previewName);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="api/series/delete/{id}")
	@ResponseBody
	public Reply deleteSerie(@PathVariable("id") long id) {
		//deleting dependencies with SERIE_ACTOR
		serieRepository.deleteFromSerieActorBySerieId(id);
		//deleting dependencies with SERIE_PRODUCTOR
		serieRepository.deleteFromSerieProductorBySerieId(id);
		serieRepository.deleteById(id);
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	

	
	
}
