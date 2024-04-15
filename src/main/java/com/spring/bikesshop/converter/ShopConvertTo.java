package com.spring.bikesshop.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.spring.bikesshop.dto.ShopDTO;
import com.spring.bikesshop.model.Shop;

public class ShopConvertTo {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	// Convierte una entidad Shop a un objeto ShopDTO
    public static ShopDTO convertToDTO(Shop shop) {
        return modelMapper.map(shop, ShopDTO.class);
    }

    // Convierte un objeto ShopDTO a una entidad Shop
    public static Shop convertToEntity(ShopDTO shopDTO) {
        return modelMapper.map(shopDTO, Shop.class);
    }

    // Convierte una lista de entidades Shop a una lista de objetos ShopDTO
    public static List<ShopDTO> convertToDTOList(List<Shop> shops) {
        return shops.stream()
                .map(ShopConvertTo::convertToDTO)
                .collect(Collectors.toList());
    }
}
