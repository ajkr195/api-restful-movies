package com.netflux.controller;

import java.io.IOException;

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
import com.netflux.model.Trailer;
import com.netflux.model.TrailerRepository;
import com.netflux.service.FileUploadTrailer;

@CrossOrigin(origins = "*")
@RestController
public class TrailerController{
	@Autowired
	private TrailerRepository trailerRepository;
	@Autowired 
	private FileUploadTrailer fileUploadTrailerService;
	
	@RequestMapping(method = RequestMethod.GET, value="api/trailers/{id}")
	@ResponseBody
	public Trailer getTrailer(@PathVariable("id") int id) {
		Trailer trailer = null;
		trailer = trailerRepository.findById(id);
		if(trailer == null) {
			throw new NotFoundException(); 
		}
		return trailer;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/api/trailers")
	@ResponseBody
	public Iterable<Trailer> getTopThree() {
		//return movieRepository.findAll();
		Iterable<Trailer> movie_list = trailerRepository.getTopThree();
		return movie_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/api/trailersbydesc")
	@ResponseBody
	public Iterable<Trailer> getAllTrailers() {
		//return movieRepository.findAll();
		Iterable<Trailer> movie_list = trailerRepository.findAllByDesc();
		return movie_list;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="api/trailer/post")
	@ResponseBody
	public Reply postTrailer(
			@RequestParam("trailerData") String trailerData, 
			@RequestParam("previewImg") MultipartFile previewImg,
			@RequestParam("imgName") String imgName
			) throws IllegalStateException, IOException {
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Trailer trailer = mapper.readValue(trailerData, Trailer.class);

		//getting last movie and setting id to new movie
		Trailer lastTrailer = trailerRepository.findTopByOrderByIdDesc();
		trailer.setId(lastTrailer.getId()+1);
	
		//insert query 
		trailerRepository.save(trailer);
		
		//Copying images
		fileUploadTrailerService.fileUpload(previewImg, imgName);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="api/trailer/edit")
	@ResponseBody
	public Reply putTrailer(
			@RequestParam("trailerData") String trailerData, 
			@RequestParam(value = "previewImg", required = false) MultipartFile previewImg,
			@RequestParam(value = "imgName", required = false) String imgName
			) throws IllegalStateException, IOException {
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Trailer trailer = mapper.readValue(trailerData, Trailer.class);
	
		//insert query 
		trailerRepository.save(trailer);
		
		//Copying images
		if(previewImg != null)
			fileUploadTrailerService.fileUpload(previewImg, imgName);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="api/trailer/delete/{id}")
	@ResponseBody
	public Reply deleterTrailer(@PathVariable("id") long id) {
		//deleting serie
		trailerRepository.deleteById(id);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
}
