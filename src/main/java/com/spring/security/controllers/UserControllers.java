package com.spring.security.controllers;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControllers {
    @Autowired
    IUserService userService;

    //Método: maneja la solicitud GET para obtener todos los usuarios
    @GetMapping("/find-all")
    private ResponseEntity<List<UserEntity>> getAllUsers(){
        //Llama al servicio de usuarios para obtener la lista de todos los usuarios y devuelve la respuesta con el estado HTTP 200 (OK)
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }
}
