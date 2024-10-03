package org.example.controllers;

import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.mappers.UserMapper;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            UserDTO createdUserDTO = userService.createUser(userCredentialsDTO.getLogin(), userCredentialsDTO.getPassword());

            return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
        try {
            UserDTO foundUserDTO = userService.getUserById(id);

            return new ResponseEntity<>(foundUserDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("id") int id, @RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            UserDTO updatedUserDTO = userService.updateUserById(id, userCredentialsDTO.getLogin(), userCredentialsDTO.getPassword());

            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") int id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
