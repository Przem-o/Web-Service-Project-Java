package PB.WebServiceProject.rest;

import PB.WebServiceProject.rest.dto.OrderDetailsDTO;
import PB.WebServiceProject.rest.dto.OrdersDTO;
import PB.WebServiceProject.services.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrdersController {

    private final OrdersService ordersService;

    @Operation(description = "Get all orders")
    @GetMapping("/orders")
    public List<OrdersDTO> getOrders(@Parameter(description = "get order by id")
                                     @RequestParam(name = "id", required = false) Long id,
                                     @Parameter(description = "get orders by minPrice", example = "1")
                                     @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                     @Parameter(description = "get orders by maxPrice", example = "10000")
                                     @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        return ordersService.getOrders(id, minPrice, maxPrice);

    }


    @PostMapping("/order/{clientId}/{productId}")
    public OrderDetailsDTO addOrderWithOrderDetails(@Parameter(description = "set client id")
                                                    @PathVariable(name = "clientId") Long clientId,
                                                    @Parameter(description = "set product id")
                                                    @PathVariable(name = "productId") Long productId,
                                                    @Parameter(description = "add new order")
                                                    @Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {

        return ordersService.addOrderedProductByClient(clientId, productId, orderDetailsDTO);
    }

    @Operation(description = "Delete order")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity deleteOrder(@Parameter(description = "set order id to delete")
                                      @PathVariable(name = "orderId") Long orderId) {
        ordersService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Get orders by client id")
    @GetMapping("/orders/{clientId}")
    public List<OrdersDTO> getClientOrders(@Parameter(description = "get orders by client id")
                                           @PathVariable(name = "clientId") Long clientId) {
        return ordersService.findClientOrders(clientId);
    }

    @Operation(description = "Edit order")
    @PutMapping("/orders/{orderId}")
    public OrdersDTO editOrder(@Parameter(description = "set order id", example = "1")
                               @PathVariable(name = "orderId") Long orderId,
                               @Valid @RequestBody OrdersDTO ordersDTO) {
        return ordersService.editOrder(orderId, ordersDTO);
    }

}


