package com.spring.bikesshop.converter;

import com.spring.bikesshop.dto.CatalogDTO;
import com.spring.bikesshop.model.Catalog;

public class CatalogConvertTo {
	
	public static CatalogDTO convertToDTO(Catalog catalog) {
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(catalog.getId());
        catalogDTO.setName(catalog.getName());
        catalogDTO.setDescription(catalog.getDescription());
        catalogDTO.setPdfUrl(catalog.getPdfUrl());
        return catalogDTO;
    }

    public static Catalog convertToEntity(CatalogDTO catalogDTO) {
        Catalog catalog = new Catalog();
        catalog.setId(catalogDTO.getId());
        catalog.setName(catalogDTO.getName());
        catalog.setDescription(catalogDTO.getDescription());
        catalog.setPdfUrl(catalogDTO.getPdfUrl());
        return catalog;
    }
}

