package com.enzoprevitale.mongo.services;

import com.enzoprevitale.mongo.dtos.UserDto;
import com.enzoprevitale.mongo.models.User;
import com.enzoprevitale.mongo.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> readAll() {
        return repository.findAll();
    }

    public Optional<User> readById(String id) {
        return repository.findById(id);
    }

    public Optional<User> readByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> readByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User create(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(encoder.encode(dto.password()));
        return repository.save(user);
    }

    public Optional<User> update(String id, UserDto dto) {
        Optional<User> user = repository.findById(id);
        return user.map(u -> {
            if(dto.username() != null) u.setUsername(dto.username());
            else if(dto.email() != null) u.setEmail(dto.email());
            else if(dto.password() != null) u.setPassword(encoder.encode(dto.password()));
            return repository.save(u);
        });
    }

    public Optional<?> delete(String id) {
        Optional<User> user = repository.findById(id);
        return user.map(u -> {
           repository.delete(u);
           return null;
        });
    }
}
