package com.spring.security.services.impl;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.persistence.repositories.UserRepository;
import com.spring.security.services.IAuthService;
import com.spring.security.services.IJWTUtilityService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import com.spring.security.services.models.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidation userValidation;

    //Creamos método para el login
    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        //Creamos un try cath
        try{
            HashMap<String, String> jwt = new HashMap<>();
            Optional<UserEntity> user = userRepository.findByEmail(login.getEmail());

            //Condicional si el usuario está vacío devuelvo un error
            if(user.isEmpty()){
                jwt.put("error", "User not registered!");
                return jwt;
            }//fin if

            //Verifica si la contraseña proporcionada por el usuario coincide con la contraseña almacenada
            if(verifyPassword(login.getPassword(), user.get().getPassword())){
                //Si las contraseñas coinciden, genera un token JWT y lo guarda en el mapa con la clave "jwt"
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
            }else{
                //Si las contraseñas no coinciden, guarda un mensaje de error en el mapa con la clave "error"
                jwt.put("error", "Authentication failed");
            }// fin if else

            return jwt;

        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    //Método para registrar un nuevo usuario
    @Override
    public ResponseDTO register(UserEntity user) throws Exception{
        try {
            //Validar los datos del usuario
            ResponseDTO response = userValidation.validation(user);

            //Si hay errores en la validación, devolver la respuesta con los errores
            if(response.getNumOfErrors() > 0){
                return response;
            }//fin if

            //Buscar todos los usuarios existentes en la base de datos
            List<UserEntity> getAllUsers = userRepository.findAll();

            //Verificar si el usuario ya existe
            for (UserEntity existingUser : getAllUsers) {

                //Comparar el email del usuario existente con el email del nuevo usuario
                if (existingUser.getEmail().equals(user.getEmail())) {
                    //Si el email ya existe, establecer un mensaje de error en la respuesta
                    response.setMessage("Email already exists!");
                    return response;
                }//fin if

                // Agrega más comparaciones de campos relevantes según sea necesario.

            }//fin for

            //Vamos a encriptar la contraseña
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            //La contraseña va a ser encriptada antes de que se guarde en al BD
            user.setPassword(encoder.encode(user.getPassword()));
            //Para guardarlo en al BD
            userRepository.save(user);
            response.setMessage("User created succesfully!");

            return response;
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
