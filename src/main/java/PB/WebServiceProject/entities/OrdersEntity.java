package PB.WebServiceProject.entities;

import PB.WebServiceProject.rest.dto.Status;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private OffsetDateTime date;
    @Column(name = "price")
    private Double price;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

    @OneToMany(mappedBy = "ordersEntity")
    private List<OrderDetailsEntity> orderDetailsEntityList;

}
