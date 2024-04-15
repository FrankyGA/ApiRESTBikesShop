package com.spring.bikesshop.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.spring.bikesshop.dto.BikeDTO;
import com.spring.bikesshop.model.Bike;

public class BikeConvertTo {

    private static final ModelMapper modelMapper = new ModelMapper();

    // Convierte una entidad Bike a un objeto BikeDTO
    public static BikeDTO convertToDTO(Bike bike) {
        return modelMapper.map(bike, BikeDTO.class);
    }

    // Convierte un objeto BikeDTO a una entidad Bike
    public static Bike convertToEntity(BikeDTO bikeDTO) {
        return modelMapper.map(bikeDTO, Bike.class);
    }

    // Convierte una lista de entidades Bike a una lista de objetos BikeDTO
    public static List<BikeDTO> convertToDTOList(List<Bike> bikes) {
        return bikes.stream()
                .map(BikeConvertTo::convertToDTO)
                .collect(Collectors.toList());
    }
}

