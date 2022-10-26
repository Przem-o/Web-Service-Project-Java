package PB.WebServiceProject.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private ClientEntity clientEntity;

}
