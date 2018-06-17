package service.entities;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<GraderUser,Long> {
    
    GraderUser findByUsername(String username);
}
