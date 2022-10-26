package PB.WebServiceProject.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    @Schema(description = "id of existing product, auto generated", example = "1")
    private Long id;
    @Schema(description = "product name", example = "Nokia3310", required = true)
    @NotBlank(message = "product name can't be blank")
    @NotNull(message = "product name can't be null")
    private String name;
    @Schema(description = "product price, value can't be negative", example = "999.99", required = true)
    @Min(value = 0, message = "min 0")
    private Double price;
    @Schema(description = "product category", example = "Smartphone", required = true)
    @NotNull(message = "product category can't be null")
    private ProductCategoryDTO productCategoryDTO;

}

