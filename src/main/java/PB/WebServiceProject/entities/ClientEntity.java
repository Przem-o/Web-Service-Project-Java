package PB.WebServiceProject.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AddressEntity addressEntity;

    @OneToMany(mappedBy = "clientEntity")
    private List<OrdersEntity> ordersEntitySet;

}
