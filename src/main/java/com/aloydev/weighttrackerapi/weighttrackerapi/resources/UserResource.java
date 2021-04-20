package com.aloydev.weighttrackerapi.weighttrackerapi.resources;

import com.aloydev.weighttrackerapi.weighttrackerapi.domain.User;
import com.aloydev.weighttrackerapi.weighttrackerapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weight_api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> userMap) {
        String username = userMap.get("username");
        String password = userMap.get("password");
        User user = userService.validateUser(username, password);
        Map<String, String> map = new HashMap<>();
        map.put("message", "logged in successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        User user = userService.registerUser(username, password);
        Map<String, String> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("password", user.getPassword());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/add_goal")
    public ResponseEntity<Map<String, String>> addUserGoal(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        double goal = Double.parseDouble(userMap.get("goal").toString());
        User user = userService.setUserGoal(username, goal);
        Map<String, String> map = new HashMap<>();
        map.put("message", "successfully set goal");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/change_permission")
    public ResponseEntity<Map<String, String>> changeUserPermission(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        int permission = (Integer) userMap.get("permission");
        User user = userService.setUserPermission(username, permission);
        Map<String, String> map = new HashMap<>();
        map.put("message", "successfully set permission");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/data_return")
    public ResponseEntity<Map<String, String>> returnUserData(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        User user = userService.getUserData(username);
        Map<String, String> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("goal", Double.toString(user.getGoal()));
        data.put("permission", Integer.toString(user.getPermission()));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}