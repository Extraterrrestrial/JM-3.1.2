package springboot.springBootMVC.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.springBootMVC.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String role);
}

