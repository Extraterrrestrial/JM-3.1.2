package springboot.springBootMVC.service;

import javassist.NotFoundException;
import springboot.springBootMVC.model.Role;
import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void add(Role role);

    void edit(Role role);

    Role getById(long id);

    Role getByName(String name) throws NotFoundException;
}
