package com.netflux.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Serie {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String img_url;
    private String img_url_preview;
    private int year_start;
    private int year_end;
    private int seasons;
    @Column(length = 1024)
    private String description; 
    private String url;
    @ColumnDefault(value = "1")
    private int media_type;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "serie_actor",
        joinColumns = @JoinColumn(name = "id_serie", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_actor", referencedColumnName = "id"))
    private List<Actor> casting;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "serie_productor",
        joinColumns = @JoinColumn(name = "id_serie", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_productor", referencedColumnName = "id"))
    private List<Productor> creators;
    
    public Serie() {  
    	super();
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

	public int getYear_start() {
		return year_start;
	}

	public void setYear_start(int year_start) {
		this.year_start = year_start;
	}

	public int getYear_end() {
		return year_end;
	}

	public void setYear_end(int year_end) {
		this.year_end = year_end;
	}

	public int getSeasons() {
		return seasons;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public String getImg_url_preview() {
		return img_url_preview;
	}

	public void setImg_url_preview(String img_url_preview) {
		this.img_url_preview = img_url_preview;
	}

	public List<Actor> getCasting() {
		return casting;
	}

	public void setCasting(List<Actor> casting) {
		this.casting = casting;
	}

	public List<Productor> getCreators() {
		return creators;
	}

	public void setCreators(List<Productor> creators) {
		this.creators = creators;
	}
	
	@Override
	public String toString() {
		return "Serie [id=" + id + ", title=" + title + ", img_url=" + img_url + ", year_start=" + year_start
				+ ", year_end=" + year_end + ", seasons=" + seasons + ", description=" + description + ", url=" + url
				+ ", media_type=" + media_type + ", casting=" + casting + "]";
	}




    
    
}
