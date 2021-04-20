package com.aloydev.weighttrackerapi.weighttrackerapi.services;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.Entry;
import com.aloydev.weighttrackerapi.weighttrackerapi.exceptions.WtAuthException;
import com.aloydev.weighttrackerapi.weighttrackerapi.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntryServiceImpl implements EntryService{

    @Autowired
    EntryRepository entryRepository;

    @Override
    public String enterNewEntryWithWeight(String username, String date, Long dateInt, Double weight) {
        Integer count = entryRepository.getCountByDateAndUsername(date, username);
        if(count > 0)
            throw new WtAuthException("Data already entered for this date.");
        return entryRepository.createWithWeight(username, dateInt, date, weight);
    }

    @Override
    public String enterNewEntryWithSleep(String username, String date, Long dateInt, Double sleep) {
        Integer count = entryRepository.getCountByDateAndUsername(date, username);
        if(count > 0)
            throw new WtAuthException("Data already entered for this date.");
        return entryRepository.createWithSleep(username, dateInt, date, sleep);
    }

    @Override
    public Entry getEntryByDateAndUsername(String date, String username) {
        return entryRepository.findByDateAndUsername(date, username);
    }

    @Override
    public Integer getCountByDateAndUsername(String username, String date) {
        return entryRepository.getCountByDateAndUsername(username, date);
    }

    @Override
    public Boolean updateSleep(String date, String username, Double sleep) {
        return entryRepository.updateSleep(date, username, sleep);
    }

    @Override
    public Boolean updateWeight(String date, String username, Double weight) {
        return entryRepository.updateWeight(date, username, weight);
    }

    @Override
    public Boolean deleteEntry(String date, String username) {
        return entryRepository.deleteEntry(date, username);
    }

    @Override
    public List<Entry> getAllEntriesByUsername(String username) {
        return entryRepository.findAllByUsername(username);
    }
}
