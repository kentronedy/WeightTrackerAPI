package com.aloydev.weighttrackerapi.weighttrackerapi.services;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.User;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;
import com.aloydev.weighttrackerapi.weighttrackerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String username, String password) throws WtAuthException {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User registerUser(String username, String password) throws WtAuthException {
        Integer count = userRepository.getCountByUsername(username);
        if(count > 0)
            throw new WtAuthException("username already in use");
        String usernameReturn = userRepository.create(username, password);
        return userRepository.findByUsername(usernameReturn);
    }

    @Override
    public User setUserGoal(String username, double goal) throws WtAuthException {
        userRepository.updateUserGoal(username, goal);
        return userRepository.findByUsername(username);
    }

    @Override
    public User setUserPermission(String username, int permission) throws WtAuthException {
        userRepository.updateUserPermission(username, permission);
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserData(String username) throws WtAuthException {
        return userRepository.findByUsername(username);
    }
}