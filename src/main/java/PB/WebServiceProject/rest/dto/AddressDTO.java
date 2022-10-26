package PB.WebServiceProject.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    @Schema(description = "client city", example = "Gdansk", required = true)
    @NotBlank(message = "city can't be blank")
    private String city;
    @Schema(description = "client country", example = "Poland", required = true)
    @NotBlank(message = "country can't be blank")
    private String country;

}
