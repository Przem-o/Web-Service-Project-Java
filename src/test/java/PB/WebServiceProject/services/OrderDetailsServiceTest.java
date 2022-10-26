package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.*;
import PB.WebServiceProject.repository.OrdersDetailsRepository;
import PB.WebServiceProject.repository.OrdersRepository;
import PB.WebServiceProject.repository.ProductsRepository;
import PB.WebServiceProject.rest.dto.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailsServiceTest {

    @Mock
    private OrdersDetailsRepository ordersDetailsRepositoryMock;
    @Mock
    private OrdersRepository ordersRepositoryMock;
    @Mock
    private ProductsRepository productsRepositoryMock;
    @InjectMocks
    private OrderDetailsService orderDetailsService;

    @Test
    public void shouldAddOrderDetail() {
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("Samsung Galaxy")
                .price(5545.99)
                .productCategoryEntity(ProductCategoryEntity.builder().build())
                .build();
        OrdersEntity ordersEntity = OrdersEntity.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(5545.99)
                .status(Status.ORDERED)
                .clientEntity(ClientEntity.builder().build())
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build();
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .id(1L)
                .quantity(1)
                .ordersEntity(ordersEntity)
                .productsEntity(productsEntity)
                .build();
        Mockito.when(ordersRepositoryMock.findById(1L)).thenReturn(Optional.of(ordersEntity));
        Mockito.when(productsRepositoryMock.findById(1L)).thenReturn(Optional.of(productsEntity));
        Mockito.when(ordersDetailsRepositoryMock.save(Mockito.any())).thenReturn(orderDetailsEntity);

        ProductsDTO productsDTO = ProductsDTO.builder()
                .id(1L)
                .name("Samsung Galaxy")
                .price(5545.99)
                .productCategoryDTO(ProductCategoryDTO.builder().build())
                .build();

        OrdersDTO ordersDTO = OrdersDTO.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(5545.99)
                .status(Status.ORDERED)
                .orderDetailsDTOList(preparedOrderDetailsDtoList())
                .build();
        OrderDetailsDTO orderDetailsDTO = OrderDetailsDTO.builder()
                .id(1L)
                .quantity(1)
                .ordersDTO(ordersDTO)
                .productsDTO(productsDTO)
                .build();
        OrderDetailsDTO orderDetailsDTO1 = orderDetailsService.addOrderDetails(1L, 1L, orderDetailsDTO);

        Assertions.assertNotNull(orderDetailsDTO1);
        Assertions.assertEquals(1L, orderDetailsDTO1.getId());
        Assertions.assertEquals("Samsung Galaxy", orderDetailsDTO1.getProductsDTO().getName());

    }

    @Test
    public void shouldReturnListOfOrderedProductsConvertedToDTO() {
        OrdersEntity ordersEntity = OrdersEntity.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(5545.99)
                .status(Status.ORDERED)
                .clientEntity(ClientEntity.builder().build())
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build();

        Mockito.when(ordersRepositoryMock.findById(1L)).thenReturn(Optional.of(ordersEntity));

        List<OrderDetailsDTO> listOfOrderedProducts = orderDetailsService.getListOfOrderedProducts(1L);
        Assertions.assertNotNull(listOfOrderedProducts);
        Assertions.assertFalse(listOfOrderedProducts.isEmpty());
        Assertions.assertEquals(3, listOfOrderedProducts.size());
    }

    @Test
    public void shouldEditOrderedProduct() {
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("Samsung Galaxy")
                .price(5545.99)
                .productCategoryEntity(ProductCategoryEntity.builder().build())
                .build();
        OrdersEntity ordersEntity = OrdersEntity.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(5545.99)
                .status(Status.ORDERED)
                .clientEntity(ClientEntity.builder().build())
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build();
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .id(1L)
                .quantity(1)
                .ordersEntity(ordersEntity)
                .productsEntity(productsEntity)
                .build();
        Mockito.when(ordersRepositoryMock.findById(1L)).thenReturn(Optional.of(ordersEntity));
        Mockito.when(ordersDetailsRepositoryMock.save(Mockito.any())).thenReturn(orderDetailsEntity);

        ProductsDTO productsDTO = ProductsDTO.builder()
                .id(1L)
                .name("Samsung Galaxy")
                .price(5545.99)
                .productCategoryDTO(ProductCategoryDTO.builder().build())
                .build();

        OrdersDTO ordersDTO = OrdersDTO.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(5545.99)
                .status(Status.ORDERED)
                .orderDetailsDTOList(preparedOrderDetailsDtoList())
                .build();
        OrderDetailsDTO orderDetailsDTO = OrderDetailsDTO.builder()
                .id(1L)
                .quantity(1)
                .ordersDTO(ordersDTO)
                .productsDTO(productsDTO)
                .build();
        OrderDetailsDTO orderDetailsDTO1 = orderDetailsService.editOrderedProduct(1L, orderDetailsDTO);

        Assertions.assertNotNull(orderDetailsDTO1);
        Assertions.assertEquals(1L, orderDetailsDTO1.getId());
        Assertions.assertEquals("Samsung Galaxy", orderDetailsDTO1.getProductsDTO().getName());

    }

    private List<OrderDetailsEntity> preparedOrderDetailEntityList() {
        List<OrderDetailsEntity> orderDetailsEntityList = new ArrayList<>();
        orderDetailsEntityList.add(OrderDetailsEntity.builder()
                .id(1L)
                .quantity(1)
                .ordersEntity(OrdersEntity.builder().build())
                .productsEntity(ProductsEntity.builder()
                        .id(1L)
                        .name("Samsung Galaxy")
                        .price(5545.99)
                        .build())
                .build());
        orderDetailsEntityList.add(OrderDetailsEntity.builder()
                .id(2L)
                .quantity(12)
                .ordersEntity(OrdersEntity.builder().build())
                .productsEntity(ProductsEntity.builder()
                        .id(1L)
                        .name("Galaxy")
                        .price(545.99)
                        .build())
                .build());
        orderDetailsEntityList.add(OrderDetailsEntity.builder()
                .id(3L)
                .quantity(13)
                .ordersEntity(OrdersEntity.builder().build())
                .productsEntity(ProductsEntity.builder()
                        .id(1L)
                        .name("Xiaomi")
                        .price(1599.99)
                        .build())
                .build());
        return orderDetailsEntityList;
    }

    private List<OrderDetailsDTO> preparedOrderDetailsDtoList() {
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        orderDetailsDTOList.add(OrderDetailsDTO.builder()
                .id(1L)
                .quantity(1)
                .ordersDTO(OrdersDTO.builder().build())
                .productsDTO(ProductsDTO.builder()
                        .id(1L)
                        .name("Samsung Galaxy")
                        .price(5545.99)
                        .build())
                .build());
        orderDetailsDTOList.add(OrderDetailsDTO.builder()
                .id(2L)
                .quantity(12)
                .ordersDTO(OrdersDTO.builder().build())
                .productsDTO(ProductsDTO.builder()
                        .id(1L)
                        .name("Galaxy")
                        .price(545.99)
                        .build())
                .build());
        orderDetailsDTOList.add(OrderDetailsDTO.builder()
                .id(3L)
                .quantity(13)
                .ordersDTO(OrdersDTO.builder().build())
                .productsDTO(ProductsDTO.builder()
                        .id(1L)
                        .name("Xiaomi")
                        .price(1599.99)
                        .build())
                .build());
        return orderDetailsDTOList;
    }
}
