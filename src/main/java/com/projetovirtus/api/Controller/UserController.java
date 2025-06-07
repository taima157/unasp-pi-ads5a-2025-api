package com.projetovirtus.api.Controller;

import com.projetovirtus.api.Models.UserModel;
import com.projetovirtus.api.Services.UserService;
import com.projetovirtus.api.ViewObject.UserViewObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserViewObject userViewObject = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userViewObject);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        UserViewObject userViewObject = userService.editUserProfile(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userViewObject);
    }
}
