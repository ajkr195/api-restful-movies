package com.netflux.model;

import java.util.List;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Actor {
	public Actor() {
		super();
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String img_url;
    
    @ManyToMany(mappedBy = "casting")
    private List<Movie> movies;
    
    @ManyToMany(mappedBy = "casting")
    private List<Serie> series;

    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Actor [id=" + id + ", name=" + name + ", img_url=" + img_url + "]";
	}
	

	
}
