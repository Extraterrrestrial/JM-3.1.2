package springboot.springBootMVC.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.springBootMVC.model.Role;
import springboot.springBootMVC.model.User;
import springboot.springBootMVC.service.RoleService;
import springboot.springBootMVC.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UsersController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        System.out.println("UsersController - конструктор -> UsersController: userService=" + userService + " roleService=" + roleService);
    }

    @GetMapping("login")
    public String log() {
        System.out.println("UsersController - log()");
        return "login";
    }

    @GetMapping("admin")
    public String getAllUsers(Model model, @AuthenticationPrincipal User user) {
        System.out.println("UsersController - getAllUsers");
        model.addAttribute("listRoles", roleService.getAllRoles());
        model.addAttribute("listUser", userService.getAllUsers());
        model.addAttribute("user", user);
        return "adminPage";
    }

    @GetMapping("user")
    public String infoUser(@AuthenticationPrincipal User user, ModelMap model) {
        System.out.println("UsersController - infoUser");
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "userPage";
    }

    @GetMapping(value = "admin/new")
    public String newUser(ModelMap model) {
        System.out.println("UsersController - newUser(ModelMap model)");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "createNew";
    }

    @PostMapping(value = "admin/new")
    public String newUser(@ModelAttribute User user,
                          @RequestParam(value = "roless") String[] role) throws NotFoundException {
        System.out.println("UsersController - newUser(@ModelAttribute User user, @RequestParam(value = \"roless\") String[] role)");
        Set<Role> rolesSet = new HashSet<>();
        for (String roles : role) {
            rolesSet.add(roleService.getByName(roles));
        }
        user.setRoles(rolesSet);
        userService.save(user);
        return "redirect:/admin";
    }
//
//    @GetMapping(value = "admin/{id}")
//    public String editUser(@PathVariable("id") long id, ModelMap model) {
//        model.addAttribute("user", userService.getById(id));
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "adminPage";
//    }

    @PostMapping(value = "admin/{id}")
    public String editUser(@ModelAttribute User user,
                           @RequestParam(value = "roless") String[] role) throws NotFoundException {
        System.out.println("UsersController - editUser)");
        Set<Role> rolesSet = new HashSet<>();
        for (String roles : role) {
            rolesSet.add((roleService.getByName(roles)));
        }
        user.setRoles(rolesSet);
        userService.edit(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "admin/{id}/del")
    public String deleteUser(@PathVariable("id") long id) {
        System.out.println("UsersController - deleteUser)");
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/admin";
    }
}
