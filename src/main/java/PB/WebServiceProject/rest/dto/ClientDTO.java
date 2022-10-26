package PB.WebServiceProject.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @Schema(description = "id of existing client", example = "1")
    private Long id;
    @Schema(description = "client name", example = "Jack Daniels", required = true)
    @NotBlank(message = "name can't be blank")
    @NotNull(message = "name can't be null")
    private String name;
    @Schema(description = "client address", required = true)
    @NotNull(message = "client address can't be null")
    private AddressDTO addressDTO;


}

