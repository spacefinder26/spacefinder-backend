package com.spacefinder.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin( origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            User newUser = userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while creating a user", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = new ArrayList<>();
        users = userService.getAllUsers();

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("id") Long id) {
        try{
            Optional<User> user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(){
        return null;
    }
}
