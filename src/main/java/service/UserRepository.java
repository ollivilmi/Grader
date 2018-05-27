package service;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<GraderUser,Long> {
    
    List<GraderUser> findByUsername(String username);
}
