package com.netflux.controller;

import java.io.IOException;
import java.math.BigInteger;
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

import com.netflux.model.MovieRepository;
import com.netflux.model.MovieDTO;
import com.netflux.model.Reply;
import com.netflux.model.Movie;
import com.netflux.model.Actor;
import com.netflux.model.ActorRepository;
import com.netflux.model.OutstandingDTO;
import com.netflux.service.FileUploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflux.exception.NotFoundException;

@CrossOrigin(origins = "*")
@RestController
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired 
	private FileUploadService fileUploadService;
	@Autowired 
	private ActorRepository actorRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="api/movies/news")
	@ResponseBody
	public Iterable<MovieDTO> getLast8Movies() {
		Iterable<Movie> movie_list = movieRepository.findTop8ByIdDesc();
		ArrayList<MovieDTO> movieDTO_list = new ArrayList<MovieDTO>();
		for(Movie m : movie_list){
			movieDTO_list.add(new MovieDTO(m.getId(), m.getTitle(), m.getImg_url(), m.getMedia_type()));
		}
		return movieDTO_list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="api/movies/all")
	@ResponseBody
	public Iterable<MovieDTO> getAllMovies() {
		Iterable<Movie> movie_list = movieRepository.findAllByDesc();
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

	
	@RequestMapping(method = RequestMethod.POST, value="api/movies/post")
	@ResponseBody
	public Reply postMovie(
			@RequestParam("mediaData") String mediaData, 
			@RequestParam("inputCover") MultipartFile imgCover, 
			@RequestParam("inputPreview") MultipartFile imgPreview,
			@RequestParam("coverName") String coverName, 
			@RequestParam("previewName") String previewName,
			@RequestParam("actorList") String actorList	
			) throws IllegalStateException, IOException {
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Movie movie = mapper.readValue(mediaData, Movie.class);
		//System.out.println(movie.toString());
		//getting last movie and setting id to new movie
		Movie lastMovie = movieRepository.findTopByOrderByIdDesc();
		movie.setId(lastMovie.getId()+1);
		//System.out.println(movie.toString());	
		//getting casting list
		if(actorList.length()>0) {
			List<Actor> casting = new ArrayList<>();
			String[] actorsArray = actorList.split(",");
			for(int i = 0; i<actorsArray.length; i++) {
				casting.add(actorRepository.findById(Long.parseLong(actorsArray[i])).get());
			}
			movie.setCasting(casting);			
		}
		//insert query 
		movieRepository.insertMovie(movie);
		
		//Copying images
		fileUploadService.fileUpload(imgCover, coverName);
		fileUploadService.fileUpload(imgPreview, previewName);
		
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="api/movies/edit")
	public @ResponseBody Reply editMovie(
			@RequestParam("mediaData") String mediaData, 
			@RequestParam(value = "inputCover", required = false) MultipartFile imgCover, 
			@RequestParam(value = "inputPreview", required = false) MultipartFile imgPreview,
			@RequestParam("coverName") String coverName, 
			@RequestParam("previewName") String previewName,
			@RequestParam("actorList") String actorList	
			) throws IllegalStateException, IOException {
		
		Reply reply = new Reply();
		
		//mapping string to movie object
		ObjectMapper mapper = new ObjectMapper();
		Movie movie;
		movie = mapper.readValue(mediaData, Movie.class);

		//getting casting list
		if(actorList.length()>0) {
			List<Actor> casting = new ArrayList<>();
			String[] actorsArray = actorList.split(",");
			for(int i = 0; i<actorsArray.length; i++) {
				casting.add(actorRepository.findById(Long.parseLong(actorsArray[i])).get());
			}
			movie.setCasting(casting);			
		}
		//insert query 
		movieRepository.insertMovie(movie);
				
		if(imgCover != null)
			fileUploadService.fileUpload(imgCover, coverName);
		if(imgPreview != null)
			fileUploadService.fileUpload(imgPreview, previewName);
		
		reply.setId(1);
		reply.setStatus("200");
		
		return reply;
	}
	

	
	@RequestMapping(method = RequestMethod.DELETE, value="api/movies/delete/{id}")
	@ResponseBody
	public Reply deleteMovie(@PathVariable("id") long id) {
		//deleting dependencies with SERIE_ACTOR
		movieRepository.deleteFromMovieActorBySerieId(id);
		//deleting serie
		movieRepository.deleteById(id);
		Reply reply = new Reply();
		reply.setId(1);
		reply.setStatus("200");
		return reply;
	}
	

}
