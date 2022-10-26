package PB.WebServiceProject.services;


import PB.WebServiceProject.entities.ProductCategoryEntity;
import PB.WebServiceProject.entities.ProductsEntity;
import PB.WebServiceProject.repository.ProductCategoryRepository;
import PB.WebServiceProject.repository.ProductsRepository;
import PB.WebServiceProject.rest.dto.ProductsDTO;
import PB.WebServiceProject.util.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductsDTO addProductsAndCategory(ProductsDTO productsDTO) { //nokia / RTV
        ProductsEntity productsEntity = EntityDtoMapper.mapProductsToEntity(productsDTO);
        ProductCategoryEntity productCategoryEntity = EntityDtoMapper.mapProdCatToEntity(productsDTO.getProductCategoryDTO());
        Long productCategoryDTOId = productsDTO.getProductCategoryDTO().getId();
        if (productCategoryDTOId != null) {
            Optional<ProductCategoryEntity> productCategoryById = productCategoryRepository.findById(productsDTO.getProductCategoryDTO().getId());
            if (productCategoryById.isPresent()) {
                productCategoryEntity = productCategoryById.get();
            }
        }
        productsEntity.setProductCategoryEntity(productCategoryEntity);
        Set<ProductsEntity> productsEntityHashSet = new HashSet<>();
        productsEntityHashSet.add(productsEntity);
        productCategoryEntity.setProductsEntitySet(productsEntityHashSet);
        productCategoryRepository.save(productCategoryEntity);
        ProductsEntity save = productsRepository.save(productsEntity);
        ProductsDTO productsDTO1 = EntityDtoMapper.mapProductsToDto(save);
        return productsDTO1;

    }

    public void deleteProducts(Long id) {
        productsRepository.deleteById(id);
    }

    public ProductsDTO editProducts(Long productId, ProductsDTO productsDTO) {
        Optional<ProductsEntity> productsById = productsRepository.findById(productId);
        if (productsById.isPresent()) {
            ProductsEntity productsEntity = productsById.get();
            productsEntity.setId(productsDTO.getId());
            productsEntity.setName(productsDTO.getName());
            productsEntity.setPrice(productsDTO.getPrice());
            ProductsEntity save = productsRepository.save(productsEntity);
            ProductsDTO productsDTO1 = EntityDtoMapper.mapProductsToDto(save);
            return productsDTO1;
        } else {
            ProductsEntity productsEntity = EntityDtoMapper.mapProductsToEntity(productsDTO);
            ProductsEntity save = productsRepository.save(productsEntity);
            ProductsDTO productsDTO1 = EntityDtoMapper.mapProductsToDto(save);
            return productsDTO1;
        }
    }

    public List<ProductsDTO> getProducts(String name, Integer minPrice, Integer maxPrice) {
        return productsRepository.findAll().stream()
                .filter(productsEntity -> name == null || productsEntity.getName().equalsIgnoreCase(name))
                .filter(productsEntity -> minPrice == null || productsEntity.getPrice() >= minPrice)
                .filter(productsEntity -> maxPrice == null || productsEntity.getPrice() <= maxPrice)
                .map(EntityDtoMapper::mapProductsToDto)
                .collect(Collectors.toList());
    }

    public List<ProductsDTO> findProductsByName(String name) {
        return findByName(name).stream()
                .map(EntityDtoMapper::mapProductsToDto)
                .collect(Collectors.toList());
    }

    private List<ProductsEntity> findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return productsRepository.findAll();
        } else {
            return productsRepository.findProductsByName(name);
        }
    }

    public List<ProductsDTO> findProductsById(Long id) {
        return productsRepository.findAll().stream()
                .filter(productsEntity -> productsEntity.getId().equals(id))
                .map(EntityDtoMapper::mapProductsToDto)
                .collect(Collectors.toList());
    }
}







