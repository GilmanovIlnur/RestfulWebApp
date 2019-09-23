package springrestapi.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import springrestapi.demo.models.User;

public interface UserRepo extends JpaRepository<User,String> {
}
