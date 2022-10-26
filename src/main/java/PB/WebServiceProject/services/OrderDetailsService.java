package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.OrderDetailsEntity;
import PB.WebServiceProject.entities.OrdersEntity;
import PB.WebServiceProject.entities.ProductsEntity;
import PB.WebServiceProject.repository.OrdersDetailsRepository;
import PB.WebServiceProject.repository.OrdersRepository;
import PB.WebServiceProject.repository.ProductsRepository;
import PB.WebServiceProject.rest.dto.OrderDetailsDTO;
import PB.WebServiceProject.util.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderDetailsService {

    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final OrdersDetailsRepository ordersDetailsRepository;

    public OrderDetailsDTO addOrderDetails(Long productId, Long ordersId, OrderDetailsDTO orderDetailsDTO) {
        OrderDetailsEntity orderDetailsEntity = EntityDtoMapper.mapOrderDetailsToEntity(orderDetailsDTO);
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(ordersId);
        Optional<ProductsEntity> productsEntity = productsRepository.findById(productId);
        orderDetailsEntity.setOrdersEntity(ordersEntity.get());
        orderDetailsEntity.setProductsEntity(productsEntity.get());
        OrderDetailsEntity save = ordersDetailsRepository.save(orderDetailsEntity);
        return EntityDtoMapper.mapOrderDetailsToDto(save);

    }

    public List<OrderDetailsDTO> getListOfOrderedProducts(Long ordersId) {
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(ordersId);
        if (ordersEntity.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrderDetailsEntity> orderDetailsEntityList = ordersEntity.get().getOrderDetailsEntityList();
        return orderDetailsEntityList
                .stream()
                .map(EntityDtoMapper::mapOrderDetailsToDto)
                .collect(Collectors.toList());

    }

    public void deleteOrderDetails(Long id) {
        ordersDetailsRepository.deleteById(id);
    }

    public OrderDetailsDTO editOrderedProduct(Long orderId, OrderDetailsDTO orderDetailsDTO) {
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);
        if (ordersEntity.isPresent()) {
            OrdersEntity ordersEntity1 = ordersEntity.get();
            OrderDetailsEntity orderDetailsEntity = EntityDtoMapper.mapOrderDetailsToEntity(orderDetailsDTO);
            orderDetailsEntity.setQuantity(orderDetailsDTO.getQuantity());
            orderDetailsEntity.setProductsEntity(EntityDtoMapper.mapProductsToEntity(orderDetailsDTO.getProductsDTO()));
            orderDetailsEntity.setOrdersEntity(ordersEntity1);
            OrderDetailsEntity save = ordersDetailsRepository.save(orderDetailsEntity);
            OrderDetailsDTO orderDetailsDTO1 = EntityDtoMapper.mapOrderDetailsToDto(save);
            return orderDetailsDTO1;
        }
        throw new NoSuchElementException("id doesn't exist ");
    }
}
