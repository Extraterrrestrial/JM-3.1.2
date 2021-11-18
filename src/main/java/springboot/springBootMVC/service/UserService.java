package springboot.springBootMVC.service;

import javassist.NotFoundException;
import springboot.springBootMVC.model.User;
import java.util.List;

public interface UserService  {

    List<User> getAllUsers();

    void save(User user);

    void delete(User user);

    void edit(User user);

    User getById(long id);

    User getByName(String name) throws NotFoundException;
    User getByEmail(String email) throws NotFoundException;
}
