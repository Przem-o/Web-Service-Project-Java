package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.ProductCategoryEntity;
import PB.WebServiceProject.entities.ProductsEntity;
import PB.WebServiceProject.repository.ProductCategoryRepository;
import PB.WebServiceProject.repository.ProductsRepository;
import PB.WebServiceProject.rest.dto.ProductCategoryDTO;
import PB.WebServiceProject.rest.dto.ProductsDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductsRepository productsRepositoryMock;
    @Mock
    private ProductCategoryRepository productCategoryRepositoryMock;
    @InjectMocks
    private ProductsService productsService;

    @Test
    public void shouldAddProductsWithCategoryToRepository() { // dorobic kategorie produktu
        //given
        ProductCategoryEntity productCategoryEntity = ProductCategoryEntity.builder().build();
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("Samsung S10")
                .price(2599.00)
                .productCategoryEntity(productCategoryEntity)
                .build();
        Mockito.when(productsRepositoryMock.save(Mockito.any())).thenReturn(productsEntity);
        Mockito.when(productCategoryRepositoryMock.save(Mockito.any())).thenReturn(productCategoryEntity);
        //when

        ProductsDTO productsDTO = ProductsDTO.builder()
                .id(1L)
                .name("Samsung S10")
                .price(2599.00)
                .productCategoryDTO(ProductCategoryDTO.builder().build())
                .build();
        ProductsDTO productsDTO1 = productsService.addProductsAndCategory(productsDTO);
        //then
        Assertions.assertNotNull(productsDTO1);
        Assertions.assertEquals("Samsung S10", productsDTO1.getName()); // spodziewam się "John" i mam w repo też mam "John"
        Assertions.assertEquals(1L, productsDTO1.getId());
    }

    @Test
    public void shouldEditProducts() {
        //given
        ProductCategoryEntity productCategoryEntity = ProductCategoryEntity.builder().build();
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("Samsung S10")
                .price(2599.00)
                .productCategoryEntity(productCategoryEntity)
                .build();
        Mockito.when(productsRepositoryMock.save(Mockito.any())).thenReturn(productsEntity);
        Mockito.when(productCategoryRepositoryMock.save(Mockito.any())).thenReturn(productCategoryEntity);
//        Mockito.when(productsRepositoryMock.findById(1L)).thenReturn(Optional.of(productsEntity));
        //when
        ProductsDTO productsDTO = ProductsDTO.builder()
                .id(1L)
                .name("Samsung S10")
                .price(2599.00)
                .productCategoryDTO(ProductCategoryDTO.builder().build())
                .build();
        ProductsDTO productsDTO1 = productsService.addProductsAndCategory(productsDTO);
        //then
        Assertions.assertNotNull(productsDTO1);
        Assertions.assertEquals("Samsung S10", productsDTO1.getName()); // spodziewam się "John" i mam w repo też mam "John"
        Assertions.assertEquals(1L, productsDTO1.getId());
    }

    @Test
    public void shouldReturnListOfProductsConvertedToDTO() {
        Mockito.when(productsRepositoryMock.findAll()).thenReturn(preparedProductList());
        List<ProductsDTO> products = productsService.getProducts("Samsung S10", 2500, 3000);
        Assertions.assertNotNull(products);
        Assertions.assertFalse(products.isEmpty());
        Assertions.assertEquals(1, products.size());

    }

    @Test
    public void shouldFindProductsByNameConvertedToDTO() {
        Mockito.when(productsRepositoryMock.findProductsByName("Xiaomi S1")).thenReturn(preparedProductList());
        List<ProductsDTO> productsDTOList = productsService.findProductsByName("Xiaomi S1");
        Assertions.assertNotNull(productsDTOList);
        Assertions.assertFalse(productsDTOList.isEmpty()); // jak lista bedzie pusta to zwroci true zamiast false
        Assertions.assertEquals(3, productsDTOList.size());
        Assertions.assertEquals("Xiaomi S1", productsDTOList.get(2).getName());

    }

    @Test
    public void shouldFindProductsById() {
        Mockito.when(productsRepositoryMock.findAll()).thenReturn(preparedProductList());
        List<ProductsDTO> productsDTOList = productsService.findProductsById(1L);
        Assertions.assertNotNull(productsDTOList);
        Assertions.assertFalse(productsDTOList.isEmpty());
        Assertions.assertEquals(1, productsDTOList.size());
        Assertions.assertEquals(1L, productsDTOList.get(0).getId());
    }

    private List<ProductsEntity> preparedProductList() {
        ProductCategoryEntity productCategoryEntity = ProductCategoryEntity.builder().build();
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("Samsung S10")
                .price(2599.00)
                .productCategoryEntity(productCategoryEntity)
                .build();
        ProductsEntity productsEntity1 = ProductsEntity.builder()
                .id(2L)
                .name("Nokia 3310")
                .price(99.00)
                .productCategoryEntity(productCategoryEntity)
                .build();
        ProductsEntity productsEntity2 = ProductsEntity.builder()
                .id(3L)
                .name("Xiaomi S1")
                .price(1599.00)
                .productCategoryEntity(productCategoryEntity)
                .build();
        List<ProductsEntity> productsEntityList = new ArrayList<>();
        productsEntityList.add(productsEntity);
        productsEntityList.add(productsEntity1);
        productsEntityList.add(productsEntity2);
        return productsEntityList;
    }

}
