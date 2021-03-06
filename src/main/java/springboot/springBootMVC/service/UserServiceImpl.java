package springboot.springBootMVC.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.springBootMVC.dao.UserDAO;
import springboot.springBootMVC.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Bean
    private BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public void save(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void edit(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    public User getById(long id) {
        User user = null;
        Optional<User> opt = userDAO.findById(id);
        if (opt.isPresent()) {
            user = opt.get();
        }
        return user;
    }

    @Override
    public User getByName(String name) throws NotFoundException {
        User user = userDAO.findByUsername(name);
        if (user == null) {
            throw new NotFoundException(name);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new NotFoundException(email);
        }
        return user;
    }

}
