package com.spring.security.services;

import com.spring.security.persistence.entities.UserEntity;

import java.util.List;

public interface IUserService {
    //Declaración del método para obtener la lista de todos los usuarios
    public List<UserEntity> findAllUsers();
}
