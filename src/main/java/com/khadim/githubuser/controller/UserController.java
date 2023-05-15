package com.khadim.githubuser.controller;

import com.khadim.githubuser.exception.ResourceNotFoundException;
import com.khadim.githubuser.github.dto.GithubRepository;
import com.khadim.githubuser.model.User;
import com.khadim.githubuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {

        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                                       @RequestBody User user) throws ResourceNotFoundException {

        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/{id}")
    public Map<String,Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {

        userService.deleteUser(userId);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/{id}/repositories")
    public ResponseEntity<List<GithubRepository>> getUserRepositoriesById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException, IOException {

        return ResponseEntity.ok().body(userService.getUserRepositoriesById(userId));
    }
}
