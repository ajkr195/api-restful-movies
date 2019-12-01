package com.netflux.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trailer {
	
	public Trailer() {
		super();
	}
	
	public Trailer(Long id, String name, String img_url, String url) {
		super();
		this.id = id;
		this.name = name;
		this.img_url = img_url;
		this.url = url;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	private String name;
	private String img_url;
	private String url;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Trailer [id=" + id + ", name=" + name + ", img_url=" + img_url + ", url=" + url + "]";
	}
	
}
