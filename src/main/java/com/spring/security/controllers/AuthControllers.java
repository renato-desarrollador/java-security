package com.spring.security.controllers;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.IAuthService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthControllers {
    @Autowired
    IAuthService authService;

    //Método: maneja la solicitud de registro de un nuevo usuario
    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) throws Exception {
        //Llama al servicio de autenticación para registrar al usuario y devuelve la respuesta con estado HTTP 201 (Created)
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    //Método: maneja la solicitud de inicio de sesión de un usuario
    @PostMapping("/login")
    private ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginRequest) throws Exception {
        //Llama al servicio de autenticación y realiza el login
        HashMap<String, String> login = authService.login(loginRequest);
        //Condicional: Verifica si la respuesta contiene un token JWT
        if(login.containsKey("jwt")){
            return new ResponseEntity<>(login, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }//fin if else
    }

}
