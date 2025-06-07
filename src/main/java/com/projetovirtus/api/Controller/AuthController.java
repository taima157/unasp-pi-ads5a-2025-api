package com.projetovirtus.api.Controller;

import com.projetovirtus.api.Models.UserModel;
import com.projetovirtus.api.Services.UserService;
import com.projetovirtus.api.ViewObject.UserViewObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserModel user) {
        UserViewObject userViewObject = userService.authenticateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(userViewObject);
    }
}
