package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.*;
import PB.WebServiceProject.repository.ClientRepository;
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
public class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepositoryMock;
    @Mock
    private ProductsRepository productsRepositoryMock;
    @Mock
    private ClientRepository clientRepositoryMock;
    @Mock
    private OrdersDetailsRepository ordersDetailsRepositoryMock;
    @InjectMocks
    private OrdersService ordersService;

    @Test
    public void shouldAddOrderToRepository() {
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("Samsung Galaxy")
                .price(5545.99)
                .productCategoryEntity(ProductCategoryEntity.builder().build())
                .build();
        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .name("Jaro")
                .addressEntity(AddressEntity.builder().build())
                .build();
        OrdersEntity ordersEntity = OrdersEntity.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(5545.99)
                .status(Status.ORDERED)
                .clientEntity(clientEntity)
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build();
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .id(1L)
                .quantity(1)
                .ordersEntity(ordersEntity)
                .productsEntity(productsEntity)
                .build();

        Mockito.when(productsRepositoryMock.findById(1L)).thenReturn(Optional.of(productsEntity));
        Mockito.when(clientRepositoryMock.findById(1L)).thenReturn(Optional.of(clientEntity));
        Mockito.when(ordersRepositoryMock.findById(1L)).thenReturn(Optional.of(ordersEntity));
        Mockito.when(ordersDetailsRepositoryMock.findAllByOrdersEntityId(1L)).thenReturn(preparedOrderDetailEntityList());
        Mockito.when(ordersRepositoryMock.save(Mockito.any())).thenReturn(ordersEntity);
        Mockito.when(ordersDetailsRepositoryMock.save(Mockito.any())).thenReturn(orderDetailsEntity);

        //dto
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
                .orderDetailsDTOList(prepareOrderDetailsDtoList())
                .build();
        OrderDetailsDTO orderDetailsDTO = OrderDetailsDTO.builder()
                .id(1L)
                .quantity(1)
                .ordersDTO(ordersDTO)
                .productsDTO(productsDTO)
                .build();
        OrderDetailsDTO orderDetailsDTO1 = ordersService.addOrderedProductByClient(1L, 1L, orderDetailsDTO);
        Assertions.assertNotNull(orderDetailsDTO1);
        Assertions.assertEquals(1L, orderDetailsDTO1.getId());
        Assertions.assertEquals("Samsung Galaxy", orderDetailsDTO1.getProductsDTO().getName());
    }

    @Test
    public void shouldReturnListOfOrdersConvertedToDTOByParameters() {
        Mockito.when(ordersRepositoryMock.findAll()).thenReturn(preparedOrdersEntityList());
        List<OrdersDTO> orders = ordersService.getOrders(1L, 5999, 6000);
//        List<OrdersDTO> orders1 = ordersService.getOrders(null, null, null);
        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());
        Assertions.assertEquals(1, orders.size());
//        Assertions.assertEquals(3, orders1.size()); opcja z nullami wtedy lista zwraca 3 elementy

    }

    @Test
    public void shouldReturnListOfClientOrdersConvertedToDTO() {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .name("Jaro")
                .addressEntity(AddressEntity.builder().build())
                .ordersEntitySet(preparedOrdersEntityList())
                .build();

        Mockito.when(clientRepositoryMock.findById(1L)).thenReturn(Optional.of(clientEntity));
        List<OrdersDTO> clientOrders = ordersService.findClientOrders(1L);
        Assertions.assertNotNull(clientOrders);
        Assertions.assertFalse(clientOrders.isEmpty());
        Assertions.assertEquals(1L, clientEntity.getId());
        Assertions.assertEquals(3, clientOrders.size());
    }

    @Test
    public void shouldEditOrder() {
        OrdersEntity ordersEntity = OrdersEntity.builder()
                .id(2L)
                .date(OffsetDateTime.now())
                .price(7001.99)
                .status(Status.ORDERED)
                .build();
        Mockito.when(ordersRepositoryMock.save(Mockito.any())).thenReturn(ordersEntity);
        OrdersDTO ordersDTO = OrdersDTO.builder()
                .id(2L)
                .date(OffsetDateTime.now())
                .price(7001.99)
                .status(Status.ORDERED)
                .build();
        OrdersDTO ordersDTO1 = ordersService.editOrder(2L, ordersDTO);
        Assertions.assertNotNull(ordersDTO1);
        Assertions.assertEquals(2L, ordersDTO1.getId());
        Assertions.assertEquals(7001.99, ordersDTO1.getPrice());

    }

    private List<OrderDetailsDTO> prepareOrderDetailsDtoList() {
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

    private List<OrdersEntity> preparedOrdersEntityList() {
        List<OrdersEntity> ordersEntityList = new ArrayList<>();
        ordersEntityList.add(OrdersEntity.builder()
                .id(1L)
                .date(OffsetDateTime.now())
                .price(6000.00)
                .status(Status.ORDERED)
                .clientEntity(ClientEntity.builder().build())
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build());
        ordersEntityList.add(OrdersEntity.builder()
                .id(2L)
                .date(OffsetDateTime.now())
                .price(7001.99)
                .status(Status.ORDERED)
                .clientEntity(ClientEntity.builder().build())
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build());
        ordersEntityList.add(OrdersEntity.builder()
                .id(3L)
                .date(OffsetDateTime.now())
                .price(200.00)
                .status(Status.ORDERED)
                .clientEntity(ClientEntity.builder().build())
                .orderDetailsEntityList(preparedOrderDetailEntityList())
                .build());
        return ordersEntityList;
    }
}

