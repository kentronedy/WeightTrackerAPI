package com.aloydev.weighttrackerapi.weighttrackerapi.repositories;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.aloydev.weighttrackerapi.weighttrackerapi.domain.Entry;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository
public class EntryRepositoryImpl implements EntryRepository{

    private static final String SQL_CREATE_WITH_WEIGHT = "INSERT INTO WT_DAILY_ENTRIES(ENTRY_ID, USERNAME, DATE_INT, DATE, WEIGHT)" +
            "VALUES(NEXTVAL('WT_DATE_ENTRIES_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_CREATE_WITH_SLEEP = "INSERT INTO WT_DAILY_ENTRIES(ENTRY_ID, USERNAME, DATE_INT, DATE, SLEEP)" +
            "VALUES(NEXTVAL('WT_DATE_ENTRIES_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_DATE_AND_USERNAME = "SELECT COUNT(*) FROM WT_DAILY_ENTRIES WHERE USERNAME = ? AND DATE = ?";
    private static final String SQL_FIND_BY_DATE_AND_USERNAME = "SELECT USERNAME, DATE_INT, DATE, WEIGHT, SLEEP " +
            "FROM WT_DAILY_ENTRIES WHERE USERNAME = ? AND DATE = ?";
    private static final String SQL_UPDATE_USER_WEIGHT = "UPDATE WT_DAILY_ENTRIES SET WEIGHT = ? WHERE USERNAME = ? AND DATE = ?";
    private static final String SQL_UPDATE_USER_SLEEP = "UPDATE WT_DAILY_ENTRIES SET SLEEP = ? WHERE USERNAME = ? AND DATE = ?";
    private static final String SQL_DELETE_ENTRY = "DELETE FROM WT_DAILY_ENTRIES WHERE USERNAME = ? AND DATE = ?";
    private static final String SQL_FIND_ALL_BY_USERNAME = "SELECT ENTRY_ID, USERNAME, DATE_INT, DATE, WEIGHT, SLEEP " +
            "FROM WT_DAILY_ENTRIES WHERE USERNAME = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String createWithWeight(String username, Long date_int, String date, Double weight) throws WtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_WITH_WEIGHT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setLong(2, date_int);
                ps.setString(3, date);
                ps.setDouble(4, weight);
                return ps;
            }, keyHolder);
            return keyHolder.getKeys().get("DATE").toString();
        } catch (Exception e) {
            throw new WtAuthException("Invalid details. Failed to create entry.");
        }
    }

    @Override
    public String createWithSleep(String username, Long date_int, String date, Double sleep) throws WtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_WITH_SLEEP, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setLong(2, date_int);
                ps.setString(3, date);
                ps.setDouble(4, sleep);
                return ps;
            }, keyHolder);
            return keyHolder.getKeys().get("DATE").toString();
        } catch (Exception e) {
            throw new WtAuthException("Invalid details. Failed to create entry.");
        }
    }

    @Override
    public Integer getCountByDateAndUsername(String date, String username) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_DATE_AND_USERNAME, new Object[]{username, date}, Integer.class);
    }

    @Override
    public Entry findByDateAndUsername(String date, String username) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_DATE_AND_USERNAME, new Object[]{username}, entryRowMapper);
    }

    @Override
    public Boolean updateWeight(String date, String username, Double weight) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_WEIGHT, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, weight);
                ps.setString(2, username);
                ps.setString(3, date);
                return ps;
            }, keyHolder);
            return weight == (Double) keyHolder.getKeys().get("WEIGHT");
        } catch (Exception e) {
            throw new WtAuthException("Unable to update weight.");
        }
    }

    @Override
    public Boolean updateSleep(String date, String username, Double sleep) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_SLEEP, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, sleep);
                ps.setString(2, username);
                ps.setString(3, date);
                return ps;
            }, keyHolder);
            return sleep == (Double) keyHolder.getKeys().get("SLEEP");
        } catch (Exception e) {
            throw new WtAuthException("Unable to update sleep.");
        }
    }

    @Override
    public Boolean deleteEntry(String date, String username) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_DELETE_ENTRY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, date);
                return ps;
            }, keyHolder);
            return date != (String) keyHolder.getKeys().get("DATE");
        } catch (Exception e) {
            throw new WtAuthException("Unable to delete.");
        }
    }

    @Override
    public List<Entry> findAllByUsername(String username) {
        List<Entry> entries = jdbcTemplate.query(SQL_FIND_ALL_BY_USERNAME, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, username);
            }
        }, entryRowMapper);
        return entries;
    }

    private RowMapper<Entry> entryRowMapper = ((rs, rowNum) -> {
        return new Entry(rs.getInt("ENTRY_ID"),
                rs.getString("USERNAME"),
                rs.getLong("DATE_INT"),
                rs.getString("DATE"),
                rs.getDouble("WEIGHT"),
                rs.getDouble("SLEEP"));
    });
}
