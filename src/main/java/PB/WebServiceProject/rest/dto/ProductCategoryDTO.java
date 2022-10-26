package PB.WebServiceProject.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {

    @Id
    private Long id;
    @Schema(description = "product category", example = "Smartphones", required = true)
    @NotBlank(message = "product category can't be blank")
    private String productCategory;

}
