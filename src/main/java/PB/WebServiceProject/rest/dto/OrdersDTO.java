package PB.WebServiceProject.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    @Schema(description = "id of existing order", example = "1")
    private Long id;
    private OffsetDateTime date;
    @Schema(description = "total price of the order", required = true)
    @NotNull
    private Double price;
    @Enumerated(EnumType.STRING)
    private Status status;
    private List<OrderDetailsDTO> orderDetailsDTOList;

}