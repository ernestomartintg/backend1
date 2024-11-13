package pe.joedayz.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.joedayz.backend.model.Product;
import pe.joedayz.backend.model.cart.CartItem;

import java.util.Optional;

public interface CarItemRepository extends JpaRepository<CartItem, Long> {

}
