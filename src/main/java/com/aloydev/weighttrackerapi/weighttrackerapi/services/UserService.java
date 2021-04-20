package com.aloydev.weighttrackerapi.weighttrackerapi.services;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.User;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;

public interface UserService {

    User validateUser(String username, String password) throws WtAuthException;

    User registerUser(String username, String password) throws WtAuthException;

    User setUserGoal(String username, double goal) throws WtAuthException;

    User setUserPermission(String username, int permission) throws WtAuthException;

    User getUserData(String username) throws WtAuthException;

}

