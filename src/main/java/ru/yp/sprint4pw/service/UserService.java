package ru.yp.sprint4pw.service;

import org.springframework.stereotype.Service;
import ru.yp.sprint4pw.model.User;
import ru.yp.sprint4pw.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void save(User user) {
        repository.save(user);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void update(Long id, User user) {
        repository.update(id, user);
    }
}
