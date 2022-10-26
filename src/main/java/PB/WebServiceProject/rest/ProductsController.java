package PB.WebServiceProject.rest;

import PB.WebServiceProject.rest.dto.ProductsDTO;
import PB.WebServiceProject.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductsController {

    private final ProductsService productsService;

    @Operation(description = "Add new product with category")
    @PostMapping("/addProductsAndCategory")
    public ProductsDTO addProductsWithCategory(@Parameter(description = "add new products and category", example = "Nokia 3310, Smartphone")
                                               @Valid @RequestBody ProductsDTO productsDTO) {
        return productsService.addProductsAndCategory(productsDTO);
    }

    @Operation(description = "Delete product by id")
    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@Parameter(description = "delete product by id", example = "1")
                                        @PathVariable(name = "id") Long id) {
        productsService.deleteProducts(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Edit product by id")
    @PutMapping("/product/{productId}")
    public ProductsDTO editProduct(@Parameter(description = "set product id", example = "1")
                                   @PathVariable(name = "productId") Long productId,
                                   @Valid @RequestBody ProductsDTO productsDTO) {
        return productsService.editProducts(productId, productsDTO);
    }

    @Operation(description = "Get products")
    @GetMapping("/products")
    public List<ProductsDTO> getProducts(@Parameter(description = "set products name", example = "Nokia3210")
                                         @RequestParam(name = "name", required = false) String name,
                                         @Parameter(description = "set products minPrice", example = "1")
                                         @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                         @Parameter(description = "set products by maxPrice", example = "10000")
                                         @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        return productsService.getProducts(name, minPrice, maxPrice);
    }

    @Operation(description = "Get products by name")
    @GetMapping("/productsByName")
    public List<ProductsDTO> findProductsByName(@Parameter(description = "find products by name", example = "Nokia3310")
                                                @RequestParam(name = "name", required = false) String name) {
        return productsService.findProductsByName(name);
    }

    @Operation(description = "Get product by id")
    @GetMapping("/product/{id}")
    public List<ProductsDTO> findProductsById(@Parameter(description = "find product by id", example = "1")
                                              @PathVariable(name = "id") Long id) {
        return productsService.findProductsById(id);
    }
}
