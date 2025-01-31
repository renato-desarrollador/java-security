package com.spring.security.services.models.validation;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.models.dtos.ResponseDTO;

public class UserValidation {

    public ResponseDTO validation(UserEntity user){
        ResponseDTO response = new ResponseDTO();

        response.setNumOfErrors(0);

        //Aquí vamos a poner las validaciones

        //Validacion: FirstName
        if(user.getFirstName() == null ||
           user.getFirstName().length() < 3 ||
           user.getFirstName().length() > 15
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo firstName no puede ser nulo, tambien tiene que tener entre 3 y 15 caracteres");
        }

        //Validacion: LastName
        if(user.getLastName() == null ||
                user.getLastName().length() < 3 ||
                user.getLastName().length() > 30
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo lastName no puede ser nulo, tambien tiene que tener entre 3 y 30 caracteres");
        }

        //Validacion: Email
        if((user.getEmail() == null ||
                !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo email no es válido.");
        }

        //Validacion: Password
        if (user.getPassword() == null ||
                !user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")
        ) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("La contraseña debe tener entre 8 y 16 caracteres, al menos un número, una minúscula y una mayúscula.");
        }

        return response;
    }
}
