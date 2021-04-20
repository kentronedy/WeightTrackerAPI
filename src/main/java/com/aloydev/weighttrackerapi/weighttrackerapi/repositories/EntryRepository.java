package com.aloydev.weighttrackerapi.weighttrackerapi.repositories;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.Entry;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;

import java.util.List;

public interface EntryRepository {

    String createWithWeight(String username, Long date_int, String date, Double weight) throws WtAuthException;

    String createWithSleep(String username, Long date_int, String date, Double sleep) throws WtAuthException;

    Integer getCountByDateAndUsername(String date, String username);

    Entry findByDateAndUsername(String date, String username);

    Boolean updateWeight(String date, String username, Double weight);

    Boolean updateSleep(String date, String username, Double sleep);

    Boolean deleteEntry(String date, String username);

    List<Entry> findAllByUsername(String username);

}
