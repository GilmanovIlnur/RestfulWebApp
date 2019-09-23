package springrestapi.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import springrestapi.demo.models.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
