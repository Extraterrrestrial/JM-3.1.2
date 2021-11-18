package springboot.springBootMVC.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.springBootMVC.dao.RoleDAO;
import springboot.springBootMVC.model.Role;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    @Override
    public void add(Role role) {
        roleDAO.save(role);
    }

    @Override
    public void edit(Role role) {
        roleDAO.save(role);
    }

    @Override
    public Role getById(long id) {
        Role role = null;
        Optional<Role> opt = roleDAO.findById(id);
        if (opt.isPresent()) {
            role = opt.get();
        }
        return role;
    }

    @Override
    public Role getByName(String name) throws NotFoundException {
        Role role = roleDAO.findByName(name);
        if (role == null) {
            throw new NotFoundException(name);
        }
        return role;
    }
}
