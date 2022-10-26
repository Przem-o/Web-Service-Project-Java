package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.ProductCategoryEntity;
import PB.WebServiceProject.repository.ProductCategoryRepository;
import PB.WebServiceProject.rest.dto.ProductCategoryDTO;
import PB.WebServiceProject.util.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDTO addCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryEntity productCategoryEntity = EntityDtoMapper.mapProdCatToEntity(productCategoryDTO);
        ProductCategoryEntity save = productCategoryRepository.save(productCategoryEntity);
        return EntityDtoMapper.mapProdCatToDto(save);
    }

    public void deleteCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }

    public ProductCategoryDTO editCategory(Long id, ProductCategoryDTO productCategoryDTO) {
        Optional<ProductCategoryEntity> byId = productCategoryRepository.findById(id);
        if (byId.isPresent()) {
            ProductCategoryEntity productCategoryEntity = byId.get();
            productCategoryEntity.setId(productCategoryDTO.getId());
            productCategoryEntity.setProductCategory(productCategoryDTO.getProductCategory());
            ProductCategoryEntity save = productCategoryRepository.save(productCategoryEntity);
            return EntityDtoMapper.mapProdCatToDto(save);
        } else {
            ProductCategoryEntity productCategoryEntity = EntityDtoMapper.mapProdCatToEntity(productCategoryDTO);
            ProductCategoryEntity save = productCategoryRepository.save(productCategoryEntity);
            return EntityDtoMapper.mapProdCatToDto(save);
        }
    }

    public List<ProductCategoryDTO> findCategory(String name) {
        return findProductCategoryByName(name).stream()
                .map(EntityDtoMapper::mapProdCatToDto)
                .collect(Collectors.toList());
    }

    private List<ProductCategoryEntity> findProductCategoryByName(String name) {
        if (StringUtils.isBlank(name)) {
            return productCategoryRepository.findAll();
        } else {
            return productCategoryRepository.findProductsByproductCategory(name);
        }
    }
}












