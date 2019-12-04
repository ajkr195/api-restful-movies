package com.netflux.model;


public class MovieDTO {
	private Long id;
    private String title;
    private String img_url;
    private int media_type;
    
    public MovieDTO(Long id, String title, String img_url, int media_type) {
		super();
		this.id = id;
		this.title = title;
		this.img_url = img_url;
		this.media_type = media_type;
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
	public int getMedia_type() {
		return media_type;
	}
	public void setMedia_type(int media_type) {
		this.media_type = media_type;
	}
    
    
    
}
