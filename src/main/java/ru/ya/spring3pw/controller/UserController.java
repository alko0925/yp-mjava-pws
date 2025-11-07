package ru.ya.spring3pw.controller;

import org.springframework.web.bind.annotation.*;
import ru.ya.spring3pw.model.User;
import ru.ya.spring3pw.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers() {
        return service.findAll();
    }

    @PostMapping(consumes = {"application/json"})
    public void save(@RequestBody User user) {
        service.save(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        service.update(id, user);
    }
}