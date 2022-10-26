package PB.WebServiceProject.rest;

import PB.WebServiceProject.rest.dto.OrderDetailsDTO;
import PB.WebServiceProject.services.OrderDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderDetailsController {

    private final OrderDetailsService ordersDetailsService;

    @Operation(description = "Add new orderDetails")
    @PostMapping("//orderDetails/{productId}/{orderId}")
    public OrderDetailsDTO addOrderDetails(@Parameter(description = "set productId")
                                           @PathVariable(name = "productId") Long productId,
                                           @Parameter(description = "set orderId")
                                           @PathVariable(name = "orderId") Long orderId,
                                           @Parameter(description = "add new orderDetails")
                                           @Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
        return ordersDetailsService.addOrderDetails(productId, orderId, orderDetailsDTO);
    }

    @Operation(description = "Delete order details by id")
    @DeleteMapping("/orderDetails/{id}")
    public ResponseEntity deleteOrder(@Parameter(description = "delete order details by id")
                                      @PathVariable(name = "id") Long id) {
        ordersDetailsService.deleteOrderDetails(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Get ordered products by orderId")
    @GetMapping("/orderDetails/{orderId}")
    public List<OrderDetailsDTO> getOrderDetail(@Parameter(description = "Get ordered products by orderId")
                                                @PathVariable(name = "orderId") Long orderId) {
        return ordersDetailsService.getListOfOrderedProducts(orderId);
    }

    @Operation(description = "Edit orderDetails")
    @PutMapping("/orderDetails/{orderId}")
    public OrderDetailsDTO editOrder(@Parameter(description = "set order Id", example = "1")
                                     @PathVariable(name = "orderId") Long orderId,
                                     @Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
        return ordersDetailsService.editOrderedProduct(orderId, orderDetailsDTO);
    }
}

