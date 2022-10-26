package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.ProductCategoryEntity;
import PB.WebServiceProject.repository.ProductCategoryRepository;
import PB.WebServiceProject.rest.dto.ProductCategoryDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductCategoryTest {

    @Mock
    private ProductCategoryRepository productCategoryRepositoryMock;
    @InjectMocks
    private ProductCategoryService productCategoryService;

    @Test
    public void shouldAddProductCategory() {
        ProductCategoryEntity productCategoryEntity = ProductCategoryEntity.builder()
                .id(1L)
                .productCategory("RTV")
                .build();
        Mockito.when(productCategoryRepositoryMock.save(Mockito.any())).thenReturn(productCategoryEntity);

        ProductCategoryDTO productCategoryDTO = ProductCategoryDTO.builder()
                .id(1L)
                .productCategory("RTV")
                .build();
        ProductCategoryDTO productCategoryDTO1 = productCategoryService.addCategory(productCategoryDTO);
        Assertions.assertNotNull(productCategoryDTO1);
        Assertions.assertEquals(1L, productCategoryDTO1.getId());
        Assertions.assertEquals("RTV", productCategoryDTO1.getProductCategory());

    }

    @Test
    public void shouldEditProductCategory() {
        ProductCategoryEntity productCategoryEntity = ProductCategoryEntity.builder()
                .id(1L)
                .productCategory("RTV")
                .build();
        Mockito.when(productCategoryRepositoryMock.findById(1L)).thenReturn(Optional.of(productCategoryEntity));
        Mockito.when(productCategoryRepositoryMock.save(Mockito.any())).thenReturn(productCategoryEntity);

        ProductCategoryDTO productCategoryDTO = ProductCategoryDTO.builder()
                .id(1L)
                .productCategory("RTV")
                .build();
        ProductCategoryDTO productCategoryDTO1 = productCategoryService.editCategory(1L, productCategoryDTO);
        Assertions.assertNotNull(productCategoryDTO1);
        Assertions.assertEquals(1L, productCategoryDTO1.getId());
        Assertions.assertEquals("RTV", productCategoryDTO1.getProductCategory());
    }

    @Test
    public void shouldFindProductCategory() {
        Mockito.when(productCategoryRepositoryMock.findProductsByproductCategory("RTV"))
                .thenReturn(preparedProductCategoryList());
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.findCategory("RTV");
        Assertions.assertNotNull(productCategoryDTOList);
        Assertions.assertEquals(1L, productCategoryDTOList.get(0).getId());
        Assertions.assertEquals("RTV", productCategoryDTOList.get(0).getProductCategory());

    }

    private List<ProductCategoryEntity> preparedProductCategoryList() {
        List<ProductCategoryEntity> productCategoryEntityList = new ArrayList<>();
        productCategoryEntityList.add(ProductCategoryEntity.builder()
                .id(1L)
                .productCategory("RTV")
                .build());
        productCategoryEntityList.add(ProductCategoryEntity.builder()
                .id(2L)
                .productCategory("AGD")
                .build());
        productCategoryEntityList.add(ProductCategoryEntity.builder()
                .id(3L)
                .productCategory("")
                .build());
        return productCategoryEntityList;
    }

}
