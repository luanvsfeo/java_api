package com.luan.javaApi.controller;

import com.luan.javaApi.domain.Message;
import com.luan.javaApi.domain.User;
import com.luan.javaApi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){

        if(user.isValidForCreation()){
            boolean exists = userService.existsUserByEmail(user.getEmail());

            if(exists){
                return new ResponseEntity<>(new Message("Email ja existente"), HttpStatus.CONFLICT);
            }else{
                user = userService.create(user);
            }
        }else{
            return new ResponseEntity<>(new Message("Dados faltantes para a criação"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        Optional<User> userlogin = userService.findByEmail(user.getEmail());

        if(userlogin.isPresent()){
            if(userlogin.get().getPassword().equals(user.getPassword())){
                return new ResponseEntity<>(userlogin, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new Message("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(new Message("Usuário e/ou senha inválidos"), HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/user/{id}")
    public ResponseEntity<?> userProfile(@PathVariable("id") Long id, HttpServletRequest request){

        String token = request.getHeader("Authorization");
        try{
            if(token == null){
                return new ResponseEntity<>(new Message("Não autorizado"), HttpStatus.UNAUTHORIZED);
            }else{
                Optional<User> user = userService.findById(id);
                if(user.isPresent()){
                    if(user.get().getToken().equals(UUID.fromString(token))){
                        if(ChronoUnit.MINUTES.between(new Date().toInstant(), user.get().getLastLogin().toInstant()) <= 30){
                            return new ResponseEntity<>(user, HttpStatus.OK);
                        }else{
                            return new ResponseEntity<>(new Message("Sessão inválida"), HttpStatus.UNAUTHORIZED);
                        }
                    }else{
                        return new ResponseEntity<>(new Message("Não autorizado"), HttpStatus.UNAUTHORIZED);
                    }
                }else{
                    return new ResponseEntity<>(new Message("Usuario não encontrado"), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (IllegalArgumentException exception){
            return new ResponseEntity<>(new Message("Token invalido"), HttpStatus.BAD_REQUEST);
        }
    }
}
