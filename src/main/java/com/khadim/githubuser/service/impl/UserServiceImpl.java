package com.khadim.githubuser.service.impl;

import com.khadim.githubuser.exception.ResourceNotFoundException;
import com.khadim.githubuser.github.GitHubService;
import com.khadim.githubuser.github.dto.GithubRepository;
import com.khadim.githubuser.model.User;
import com.khadim.githubuser.repository.UserRepository;
import com.khadim.githubuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private GitHubService githubService;


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) throws ResourceNotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        user.setFirstName(userDetails.getFirstName());
        user.setSurName(userDetails.getSurName());
        user.setPosition(userDetails.getPosition());
        user.setGitHubUrl(userDetails.getGitHubUrl());
        final User updatedUser = userRepository.save(user);
        return updatedUser;

    }

    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
    }

    @Override
    public List<GithubRepository> getUserRepositoriesById(Long id) throws ResourceNotFoundException, IOException {
        final  User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        String urlParts [] = user.getGitHubUrl().split("/");

        String gitHubUsername = urlParts[urlParts.length-1];
        return githubService.getRepositoriesByUser(gitHubUsername);
    }
}
