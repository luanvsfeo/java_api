package com.luan.javaApi.controller;

import com.luan.javaApi.domain.Message;
import com.luan.javaApi.domain.User;
import com.luan.javaApi.enumx.MessageDefault;
import com.luan.javaApi.service.UserService;
import com.luan.javaApi.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){

        if(user.isValidForCreation()){
            boolean exists = userService.existsUserByEmail(user.getEmail());

            if(exists){
                return new ResponseEntity<>(new Message(MessageDefault.EMAIL_EXISTS.getMensagem()), MessageDefault.EMAIL_EXISTS.getStatus());
            }else{
                user = userService.create(user);
            }
        }else{
            return new ResponseEntity<>(new Message(MessageDefault.MISSING_CORE_ATTRIBUTES.getMensagem()), MessageDefault.MISSING_CORE_ATTRIBUTES.getStatus());
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        Optional<User> userlogin = userService.findByEmail(user.getEmail());

        if(userlogin.isPresent()){
            if(StringUtils.compareCriptedPasswordWithNoCriptedPassword(userlogin.get().getPassword(), user.getPassword())){
                userService.update(userlogin.get());
                return new ResponseEntity<>(userlogin, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new Message(MessageDefault.INVALID_USER_OR_PASSWORD), HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(new Message(MessageDefault.INVALID_USER_OR_PASSWORD), HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/user/{id}")
    public ResponseEntity<?> userProfile(@PathVariable("id") Long id, HttpServletRequest request){

        String token = request.getHeader("Authorization");
        Optional<User> user = userService.findById(id);
        MessageDefault message =  userService.isInvalid(token, user.orElse(null));

        if(message == null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Message(message), message.getStatus());
        }
    }
}
