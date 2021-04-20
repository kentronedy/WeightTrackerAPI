package com.aloydev.weighttrackerapi.weighttrackerapi.repositories;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.User;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;

public interface UserRepository {

    String create(String username, String password) throws WtAuthException;

    User findByUsernameAndPassword(String username, String password) throws WtAuthException;

    Integer getCountByUsername(String username);

    User findByUsername(String username);

    Boolean updateUserGoal(String username, double goal) throws WtAuthException;

    Boolean updateUserPermission(String username, int permission) throws WtAuthException;

}

