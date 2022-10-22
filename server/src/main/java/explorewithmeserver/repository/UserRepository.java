package explorewithmeserver.repository;

import explorewithmeserver.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByIdInOrderByIdAsc(List<Long> ids, Pageable pageable);

}
