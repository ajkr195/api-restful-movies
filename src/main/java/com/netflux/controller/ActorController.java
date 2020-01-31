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
import com.netflux.model.Actor;
import com.netflux.model.ActorRepository;
import com.netflux.model.Reply;
import com.netflux.service.FileUploadActorService;

@CrossOrigin(origins = "*")
@RestController
public class ActorController {
	@Autowired
	private ActorRepository actorRepository;
	@Autowired 
	private FileUploadActorService fileUploadService;
	
	@RequestMapping(method = RequestMethod.GET, value="api/actors")
	@ResponseBody
	public Iterable<Actor> getAllActors() {
		Iterable<Actor> actor_list = actorRepository.findAll();
		return actor_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/actorsdesc")
	@ResponseBody
	public Iterable<Actor> getAllActorsByDesc() {
		Iterable<Actor> actor_list = actorRepository.findAllByDesc();
		return actor_list;
	}

	@RequestMapping(method = RequestMethod.POST, value="api/actor/post")
	@ResponseBody
	public Reply postActor(
			@RequestParam("actorData") String actorData,
			@RequestParam("imgName") String imgName,
			@RequestParam("imgActor") MultipartFile imgActor
			) throws IllegalStateException, IOException {
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Actor actor = mapper.readValue(actorData, Actor.class);
		//getting and setting last actor id
		Actor lastActor = actorRepository.findTopByOrderByIdDesc();
		actor.setId(lastActor.getId()+1);
		actorRepository.save(actor);
		
		//Copying images
		fileUploadService.fileUpload(imgActor, imgName);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="api/actor/delete/{id}")
	@ResponseBody
	public Reply deleteActor(@PathVariable("id") long id) {
		//deleting dependencies with MOVIE_ACTOR
		actorRepository.deleteFromMovieActorByActorId(id);
		//deleting dependencies with SERIE_ACTOR
		actorRepository.deleteFromSerieActorByActorId(id);
		//deleting serie
		actorRepository.deleteById(id);
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
}
