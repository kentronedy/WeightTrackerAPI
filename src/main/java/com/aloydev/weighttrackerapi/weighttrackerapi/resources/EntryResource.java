package com.aloydev.weighttrackerapi.weighttrackerapi.resources;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.Entry;
import com.aloydev.weighttrackerapi.weighttrackerapi.domain.User;
import com.aloydev.weighttrackerapi.weighttrackerapi.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weight_api/entries")
public class EntryResource {

    @Autowired
    EntryService entryService;

    @GetMapping("/get_entries")
    public ResponseEntity<Map<Long, Map<String, String>>> returnUserData() {
        String username = "troy2";
        List<Entry> entries = entryService.getAllEntriesByUsername(username);
        Map<Long, Map<String, String>> outer = new HashMap<>();
        for (Entry entry : entries) {
            Map<String, String> inner = new HashMap<>();
            inner.put("date", entry.getDate());
            inner.put("weight", Double.toString(entry.getWeight()));
            inner.put("sleep", Double.toString(entry.getSleep()));
            outer.put(entry.getDateInt(), inner);
        }
        return new ResponseEntity<>(outer, HttpStatus.OK);
    }

    @PostMapping("/entry_weight")
    public ResponseEntity<Map<String, String>> entryWeight(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        Long dateInt = (Long) userMap.get("dateInt");
        Double weight = Double.parseDouble(userMap.get("weight").toString());
        String returnDate = entryService.enterNewEntryWithWeight(username, date, dateInt, weight);
        Map<String, String> data = new HashMap<>();
        data.put("Date of entry", returnDate);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/entry_sleep")
    public ResponseEntity<Map<String, String>> entrySleep(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        Long dateInt = (Long) userMap.get("dateInt");
        Double sleep = Double.parseDouble(userMap.get("sleep").toString());
        String returnDate = entryService.enterNewEntryWithSleep(username, date, dateInt, sleep);
        Map<String, String> data = new HashMap<>();
        data.put("Date of entry", returnDate);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/update_weight")
    public ResponseEntity<Map<String, String>> addUserGoal(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        Double weight = Double.parseDouble(userMap.get("weight").toString());
        String isUpdated = entryService.updateWeight(date, username, weight).toString();
        Map<String, String> data = new HashMap<>();
        data.put("Was Updated", isUpdated);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/update_sleep")
    public ResponseEntity<Map<String, String>> changeUserPermission(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        Double sleep = Double.parseDouble(userMap.get("sleep").toString());
        String isUpdated = entryService.updateSleep(date, username, sleep).toString();
        Map<String, String> data = new HashMap<>();
        data.put("Was Updated", isUpdated);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/count")
    public ResponseEntity<Map<String, String>> countEntries(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        int count = entryService.getCountByDateAndUsername(username, date);
        Map<String, String> data = new HashMap<>();
        data.put("Number of entries", Integer.toString(count));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/get_entry")
    public ResponseEntity<Map<String, String>> getEntry(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        Entry entry = entryService.getEntryByDateAndUsername(date, username);
        Map<String, String> data = new HashMap<>();
        data.put("username", entry.getUsername());
        data.put("sleep", entry.getSleep().toString());
        data.put("weight", entry.getWeight().toString());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/delete_entry")
    public ResponseEntity<Map<String, String>> deleteEntry(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String date = (String) userMap.get("date");
        boolean isDeleted = entryService.deleteEntry(date, username);
        Map<String, String> data = new HashMap<>();
        data.put("Deletion outcome", Boolean.toString(isDeleted));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/return_entries")
    public ResponseEntity<Map<Long, Map<String, String>>> returnUserData(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        List<Entry> entries = entryService.getAllEntriesByUsername(username);
        Map<Long, Map<String, String>> outer = new HashMap<>();
        for(Entry entry : entries){
            Map<String, String> inner = new HashMap<>();
            inner.put("date", entry.getDate());
            inner.put("weight", Double.toString(entry.getWeight()));
            inner.put("sleep", Double.toString(entry.getSleep()));
            outer.put(entry.getDateInt(), inner);
        }
        return new ResponseEntity<>(outer, HttpStatus.OK);
    }
}
