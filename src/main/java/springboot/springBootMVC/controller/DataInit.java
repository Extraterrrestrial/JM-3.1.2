package springboot.springBootMVC.controller;

import javassist.NotFoundException;
import springboot.springBootMVC.model.Role;
import springboot.springBootMVC.model.User;
import springboot.springBootMVC.service.RoleService;
import springboot.springBootMVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInit {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void dataInit() throws NotFoundException {
        System.out.println("DataInit - dataInit");

        List<Role> allRoles = roleService.getAllRoles();
        if (allRoles.size() == 0) {
            System.out.println("DataInit - dataInit - if (allRoles.size() == 0)");
            roleService.add(new Role(1L,"ROLE_ADMIN"));
            roleService.add(new Role(2L,"ROLE_USER"));
        }

        List<User> allUsers = userService.getAllUsers();
        if (allUsers.size() == 0) {
            System.out.println("DataInit - dataInit - if (allUsers.size() == 0)");
            User user1 = new User();

            Set<Role> rolesSet = new HashSet<>();
            rolesSet.add(roleService.getByName("ROLE_ADMIN"));

            user1.setUsername("admin");
            user1.setLastname("admin");
            user1.setAge((byte) 15);
            user1.setEmail("admin@test.com");
            user1.setPassword("admin");
            user1.setRoles(rolesSet);
            userService.save(user1);
        }
    }
}