package com.spring.bikesshop.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Catalog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private String description;

    @Column(nullable = false)
    private String pdfUrl; // Ruta del archivo PDF en el sistema de archivos

    public Catalog() {}

	public Catalog(Long id, String name, String description, String pdfUrl) {
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

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, pdfUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Catalog other = (Catalog) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(pdfUrl, other.pdfUrl);
	}

	@Override
	public String toString() {
		return "Catalog [id=" + id + ", name=" + name + ", description=" + description + ", pdfUrl=" + pdfUrl + "]";
	}
    
    
    
}

