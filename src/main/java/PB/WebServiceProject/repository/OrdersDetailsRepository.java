package PB.WebServiceProject.repository;

import PB.WebServiceProject.entities.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

    List<OrderDetailsEntity> findAllByOrdersEntityId(Long id);

}
