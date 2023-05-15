package com.khadim.githubuser.service;

import com.khadim.githubuser.exception.ResourceNotFoundException;
import com.khadim.githubuser.github.dto.GithubRepository;
import com.khadim.githubuser.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUserById(Long id) throws ResourceNotFoundException;

    User saveUser(User user);

    User updateUser(Long id, User user) throws ResourceNotFoundException;

    void deleteUser(Long userId) throws ResourceNotFoundException;

    List<GithubRepository> getUserRepositoriesById(Long id) throws ResourceNotFoundException, IOException;

}
