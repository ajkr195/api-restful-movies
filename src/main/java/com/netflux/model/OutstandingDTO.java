package com.netflux.model;

public class OutstandingDTO {
    
	private Long id;
    private String title;
    private String img_url_preview;
    private int media_type;
    
    public OutstandingDTO(Long id, String title, String img_url_preview, int media_type) {
		this.id = id;
		this.title = title;
		this.img_url_preview = img_url_preview;
		this.media_type = media_type;
	}
	public OutstandingDTO() {
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
	public String getImg_url_preview() {
		return img_url_preview;
	}
	public void setImg_url_preview(String img_url_preview) {
		this.img_url_preview = img_url_preview;
	}
	public int getMedia_type() {
		return media_type;
	}
	public void setMedia_type(int media_type) {
		this.media_type = media_type;
	}
    
    
}
