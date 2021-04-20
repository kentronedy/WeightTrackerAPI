package com.aloydev.weighttrackerapi.weighttrackerapi.repositories;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.User;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Locale;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO WT_USERS(USERNAME, PASSWORD)" +
            "VALUES(?, ?)";
    private static final String SQL_COUNT_BY_USERNAME = "SELECT COUNT(*) FROM WT_USERS WHERE USERNAME = ?";
    private static final String SQL_FIND_BY_USERNAME = "SELECT USERNAME, PASSWORD, GOAL, PERMISSION " +
            "FROM WT_USERS WHERE USERNAME = ?";
    private static final String SQL_UPDATE_USER_GOAL = "UPDATE WT_USERS SET GOAL = ? WHERE USERNAME = ?";
    private static final String SQL_UPDATE_USER_PERMISSION = "UPDATE WT_USERS SET PERMISSION = ? WHERE USERNAME = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String create(String username, String password) throws WtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                return ps;
            }, keyHolder);
            return keyHolder.getKeys().get("USERNAME").toString();
        } catch (Exception e) {
            throw new WtAuthException("Invalid details. Failed to create account.");
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws WtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, new Object[]{username}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new WtAuthException("Invalid email/password");
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new WtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByUsername(String username) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_USERNAME, new Object[]{username}, Integer.class);
    }

    @Override
    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, new Object[]{username}, userRowMapper);
    }

    @Override
    public Boolean updateUserGoal(String username, double goal) throws WtAuthException{
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_GOAL, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, goal);
                ps.setString(2, username);
                return ps;
            }, keyHolder);
            return goal == (Double) keyHolder.getKeys().get("GOAL");
        } catch (Exception e) {
            throw new WtAuthException("Unable to update user goal.");
        }
    }

    @Override
    public Boolean updateUserPermission(String username, int permission) throws WtAuthException{
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_PERMISSION, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, permission);
                ps.setString(2, username);
                return ps;
            }, keyHolder);
            return permission == (Integer) keyHolder.getKeys().get("PERMISSION");
        } catch (Exception e) {
            throw new WtAuthException("Unable to update user permission.");
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getString("USERNAME"),
                rs.getString("PASSWORD"),
                rs.getDouble("GOAL"),
                rs.getInt("PERMISSION"));
    });
}

