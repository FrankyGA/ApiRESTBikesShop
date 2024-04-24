package com.spring.bikesshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.bikesshop.dto.CatalogDTO;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.exceptions.ValidationException;
import com.spring.bikesshop.model.Catalog;
import com.spring.bikesshop.repository.CatalogRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Catalog", description = "Catalog controller with CRUD Operations")
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogController(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Operation(summary = "Get all catalogs", description = "Get a list of all catalogs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Catalogs found",
                     content = @Content(mediaType = "application/json",
                                       array = @ArraySchema(schema = @Schema(implementation = CatalogDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No catalogs found")
    })
    @GetMapping("/")
    public ResponseEntity<List<CatalogDTO>> getAllCatalogs() {
        List<Catalog> catalogs = catalogRepository.findAll();
        if (catalogs.isEmpty()) {
            throw new ResourceNotFoundException("No catalogs found");
        }
        List<CatalogDTO> catalogDTOs = catalogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(catalogDTOs);
    }

    @Operation(summary = "Get catalog by ID", description = "Get a catalog by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catalog found", content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "404", description = "Catalog not found with id: ...")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CatalogDTO> getCatalogById(@PathVariable Long id) {
        Optional<Catalog> catalogOptional = catalogRepository.findById(id);
        Catalog catalog = catalogOptional.orElseThrow(() -> new ResourceNotFoundException("Catalog not found with id: " + id));
        return ResponseEntity.ok(convertToDTO(catalog));
    }

    @Operation(summary = "Create a new catalog", description = "Create a new catalog with PDF file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catalog created"),
            @ApiResponse(responseCode = "400", description = "Invalid catalog data provided")
    })
    @PostMapping("/")
    public ResponseEntity<CatalogDTO> createCatalog( @RequestBody CatalogDTO catalogDTO) {
    	if (catalogDTO == null) {
            throw new ValidationException("Invalid catalog data provided");
        }
        Catalog catalog = convertToEntity(catalogDTO);
        Catalog savedCatalog = catalogRepository.save(catalog);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedCatalog));
    }

    @Operation(summary = "Update catalog by ID", description = "Update an existing catalog by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Catalog updated",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = CatalogDTO.class))),
        @ApiResponse(responseCode = "404", description = "Catalog not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CatalogDTO> updateCatalog(@PathVariable Long id, @Valid @RequestBody CatalogDTO updatedCatalogDTO) {
        Optional<Catalog> catalogOptional = catalogRepository.findById(id);
        Catalog catalog = catalogOptional.orElseThrow(() -> new ResourceNotFoundException("Catalog not found with id: " + id));
        catalog.setName(updatedCatalogDTO.getName());
        catalog.setDescription(updatedCatalogDTO.getDescription());
        catalog.setPdfUrl(updatedCatalogDTO.getPdfUrl());
        Catalog savedCatalog = catalogRepository.save(catalog);
        return ResponseEntity.ok(convertToDTO(savedCatalog));
    }

    @Operation(summary = "Delete catalog by ID", description = "Delete a catalog by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Catalog deleted"),
            @ApiResponse(responseCode = "404", description = "Catalog not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Long id) {
        if (catalogRepository.existsById(id)) {
            catalogRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Catalog not found with id: " + id);
        }
    }

    @Operation(summary = "Download catalog PDF", description = "Download the PDF file of a catalog by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PDF file downloaded successfully"),
        @ApiResponse(responseCode = "404", description = "Catalog not found")
    })
    // Petición descarga archivo PDF
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadCatalogPdf(@PathVariable Long id) throws MalformedURLException {
        Optional<Catalog> catalogOptional = catalogRepository.findById(id);
        Catalog catalog = catalogOptional.orElseThrow(() -> new ResourceNotFoundException("Catalog not found with id: " + id));

        // Descarga de un archivo PDF
        Path pdfPath = Paths.get(catalog.getPdfUrl());
        Resource resource = new UrlResource(pdfPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private CatalogDTO convertToDTO(Catalog catalog) {
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(catalog.getId());
        catalogDTO.setName(catalog.getName());
        catalogDTO.setDescription(catalog.getDescription());
        catalogDTO.setPdfUrl(catalog.getPdfUrl());
        return catalogDTO;
    }

    private Catalog convertToEntity(CatalogDTO catalogDTO) {
        Catalog catalog = new Catalog();
        catalog.setId(catalogDTO.getId());
        catalog.setName(catalogDTO.getName());
        catalog.setDescription(catalogDTO.getDescription());
        catalog.setPdfUrl(catalogDTO.getPdfUrl());
        return catalog;
    }
}


