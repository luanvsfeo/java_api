package com.luan.javaApi.service;

import com.luan.javaApi.domain.User;
import com.luan.javaApi.enumx.MessageDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void dadoUsuarioTokenInvalido(){

        User user = userService.create(mockUsuario("teste@gmail.com","abc123","Teste"));

        MessageDefault messageDefault = userService.isInvalid("teste",user);

        Assertions.assertEquals(MessageDefault.INVALID_TOKEN, messageDefault);
    }

    @Test
    void dadoUsuarioTokenExpirado(){

        User user = userService.create(mockUsuario("teste@gmail.com","abc123","Teste"));

        user.setLastLogin(convertStringToDate("18/03/2021 - 15:01"));

        MessageDefault messageDefault = userService.isInvalid(user.getToken().toString(),user);

        Assertions.assertEquals(MessageDefault.INVALID_SESSION, messageDefault);
    }

    @Test
    void dadoUsuarioInexistente(){

        MessageDefault messageDefault = userService.isInvalid("teste",null);

        Assertions.assertEquals(MessageDefault.USER_NOT_FOUND, messageDefault);
    }

    @Test
    void dadoTokenInexistente(){

        MessageDefault messageDefault = userService.isInvalid(null,mockUsuario("teste@gmail.com","abc123","Teste"));

        Assertions.assertEquals(MessageDefault.UNAUTHORIZED, messageDefault);
    }


    User mockUsuario(String email, String password, String name){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return user;
    }

    Date convertStringToDate(String date){
        try {
            return new SimpleDateFormat("dd/MM/yyyy - HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
