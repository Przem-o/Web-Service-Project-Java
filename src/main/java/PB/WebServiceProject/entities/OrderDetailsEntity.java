package PB.WebServiceProject.entities;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orderdetails")
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private OrdersEntity ordersEntity;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private ProductsEntity productsEntity;

}
