package com.spring.bikesshop.dto;

public class CatalogDTO {
	
	private Long id;
    private String name;
    private String description;
    private String pdfUrl;
    
    public CatalogDTO() {}

	public CatalogDTO(Long id, String name, String description, String pdfUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.pdfUrl = pdfUrl;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

}
