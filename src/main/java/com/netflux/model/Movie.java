package com.netflux.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Movie {
	


	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String img_url;
    private String img_url_preview;
    private String duration;
    @Column(length = 1024)
    private String description;
    private String director;
    private String url;
    private String year;
    @ColumnDefault(value = "1")
    private int media_type;
    @ColumnDefault(value = "0")
    private int outstanding;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_actor",
        joinColumns = @JoinColumn(name = "id_movie", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_actor", referencedColumnName = "id"))
    private List<Actor> casting;
    
    
    public Movie() {  
    	super();
    }

    //constructor de prueba POST
    public Movie (String title, String duration, String year, String description, String director) {
    	this.title = title;
    	this.duration = duration;
    	this.year = year;
    	this.description = description;
    	this.director = director;
    }
    
	public Movie(Long id, String title, String img_url_preview, int media_type) {
		this.id = id;
		this.title = title;
		this.img_url_preview = img_url_preview;
		this.media_type = media_type;
	}
    
    
	public Movie(Long id, String title, String img_url, String duration, String description, String director) {
		super();
		this.id = id;
		this.title = title;
		this.img_url = img_url;
		this.duration = duration;
		this.description = description;
		this.director = director;
		this.casting = new ArrayList<>();
	}  

	public Movie(Long id, String title, String img_url, String duration, String description, String director,
			List<Actor> casting) {
		super();
		this.id = id;
		this.title = title;
		this.img_url = img_url;
		this.duration = duration;
		this.description = description;
		this.director = director;
		this.casting = casting;
	}      
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
	public String getImg_url_preview() {
		return img_url_preview;
	}

	public void setImg_url_preview(String img_url_preview) {
		this.img_url_preview = img_url_preview;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getMedia_type() {
		return media_type;
	}

	public void setMedia_type(int media_type) {
		this.media_type = media_type;
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}	
	
	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}
	
	public List<Actor> getCasting() {
		return casting;
	}
	public void setCasting(List<Actor> casting) {
		this.casting = casting;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", img_url=" + img_url + ", img_url_preview=" + img_url_preview
				+ ", duration=" + duration + ", description=" + description + ", director=" + director + ", url=" + url
				+ ", year=" + year + ", media_type=" + media_type + ", outstanding=" + outstanding + ", casting="
				+ casting + "]";
	}

}
