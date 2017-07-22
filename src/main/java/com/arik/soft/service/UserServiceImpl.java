package com.arik.soft.service;

import com.arik.soft.domain.UserRepository;
import com.arik.soft.service.representation.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<UserRepresentation> getUserDetails() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .map(UserRepresentation::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserRepresentation saveUser(UserRepresentation userData) {
        return new UserRepresentation(
                userRepository.save(UserRepresentation.getUser(userData))
        );
    }

    @Override
    @Transactional
    public UserRepresentation getUserById(Long userId) {
        return new UserRepresentation(userRepository.findOne(userId));
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.delete(userId);
    }

    @Override
    public UserRepresentation getUserByUserName(String userName) {
        return userRepository
                .findByUserName(userName)
                .map(UserRepresentation::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}