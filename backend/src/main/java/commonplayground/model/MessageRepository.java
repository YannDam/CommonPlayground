package commonplayground.model;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Message findAllById(Long userId);
    Message findAllByDescription(String description);
}
