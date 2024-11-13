package pe.joedayz.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.joedayz.backend.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
