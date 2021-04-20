package com.aloydev.weighttrackerapi.weighttrackerapi.services;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.Entry;

import java.util.List;

public interface EntryService {

    String enterNewEntryWithWeight(String username, String date, Long dateInt, Double weight);

    String enterNewEntryWithSleep(String username, String date, Long dateInt, Double sleep);

    Entry getEntryByDateAndUsername(String date, String username);

    Integer getCountByDateAndUsername(String username, String date);

    Boolean updateSleep(String date, String username, Double sleep);

    Boolean updateWeight(String date, String username, Double weight);

    Boolean deleteEntry(String date, String username);

    List<Entry> getAllEntriesByUsername(String username);
}
