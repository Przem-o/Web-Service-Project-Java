package PB.WebServiceProject.util;

import PB.WebServiceProject.entities.*;
import PB.WebServiceProject.rest.dto.*;
import org.springframework.beans.BeanUtils;

public class EntityDtoMapper {

    public static ClientDTO mapClientToDto(ClientEntity clientEntity) {
        ClientDTO clientDTO = ClientDTO.builder().build();
        BeanUtils.copyProperties(clientEntity, clientDTO);
        if (clientEntity.getAddressEntity() != null) {
            clientDTO.setAddressDTO(EntityDtoMapper.mapAddressToDto(clientEntity.getAddressEntity()));
        }
        return clientDTO;
    }

    public static ClientEntity mapClientToEntity(ClientDTO clientDTO) {
        ClientEntity clientEntity = ClientEntity.builder().build();
        BeanUtils.copyProperties(clientDTO, clientEntity);
        if (clientDTO.getAddressDTO() != null) {
            clientEntity.setAddressEntity(EntityDtoMapper.mapAddressToEntity(clientDTO.getAddressDTO()));
        }
        return clientEntity;
    }

    public static AddressDTO mapAddressToDto(AddressEntity addressEntity) {
        AddressDTO addressDTO = AddressDTO.builder().build();
        BeanUtils.copyProperties(addressEntity, addressDTO);
        return addressDTO;
    }

    public static AddressEntity mapAddressToEntity(AddressDTO addressDTO) {
        AddressEntity addressEntity = AddressEntity.builder().build();
        BeanUtils.copyProperties(addressDTO, addressEntity);
        return addressEntity;
    }

    public static ProductsDTO mapProductsToDto(ProductsEntity productsEntity) {
        ProductsDTO productsDTO = ProductsDTO.builder().build();
        BeanUtils.copyProperties(productsEntity, productsDTO);
        if (productsEntity.getProductCategoryEntity() != null) {
            productsDTO.setProductCategoryDTO(EntityDtoMapper.mapProdCatToDto(productsEntity.getProductCategoryEntity()));
        }
        return productsDTO;

    }

    public static ProductsEntity mapProductsToEntity(ProductsDTO productsDTO) {
        ProductsEntity productsEntity = ProductsEntity.builder().build();
        BeanUtils.copyProperties(productsDTO, productsEntity);
        if (productsEntity.getProductCategoryEntity() != null) {
            productsEntity.setProductCategoryEntity(EntityDtoMapper.mapProdCatToEntity(productsDTO.getProductCategoryDTO()));
        }
        return productsEntity;
    }

    public static ProductCategoryDTO mapProdCatToDto(ProductCategoryEntity productCategoryEntity) {
        ProductCategoryDTO productCategoryDTO = ProductCategoryDTO.builder().build();
        BeanUtils.copyProperties(productCategoryEntity, productCategoryDTO);
        return productCategoryDTO;
    }

    public static ProductCategoryEntity mapProdCatToEntity(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryEntity productCategoryEntity = ProductCategoryEntity.builder().build();
        BeanUtils.copyProperties(productCategoryDTO, productCategoryEntity);
        return productCategoryEntity;
    }

    public static OrdersDTO mapOrdersToDto(OrdersEntity ordersEntity) {
        OrdersDTO ordersDTO = OrdersDTO.builder().build();
        BeanUtils.copyProperties(ordersEntity, ordersDTO);
        return ordersDTO;

    }

    public static OrdersEntity mapOrdersToEntity(OrdersDTO ordersDTO) {
        OrdersEntity ordersEntity = OrdersEntity.builder().build();
        BeanUtils.copyProperties(ordersDTO, ordersEntity);
        return ordersEntity;
    }

    public static OrderDetailsDTO mapOrderDetailsToDto(OrderDetailsEntity orderDetailsEntity) {
        OrderDetailsDTO orderDetailsDTO = OrderDetailsDTO.builder().build();
        BeanUtils.copyProperties(orderDetailsEntity, orderDetailsDTO);
        if (orderDetailsEntity.getProductsEntity() != null) {
            orderDetailsDTO.setProductsDTO(EntityDtoMapper.mapProductsToDto(orderDetailsEntity.getProductsEntity()));
        }
        return orderDetailsDTO;
    }

    public static OrderDetailsEntity mapOrderDetailsToEntity(OrderDetailsDTO orderDetailsDTO) {
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder().build();
        BeanUtils.copyProperties(orderDetailsDTO, orderDetailsEntity);
        return orderDetailsEntity;
    }
}




