package com.luan.javaApi.service;

import com.luan.javaApi.domain.User;
import com.luan.javaApi.enumx.MessageDefault;
import com.luan.javaApi.repository.PhoneRepository;
import com.luan.javaApi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;

    public UserService(UserRepository userRepository, PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
    }

    public boolean existsUserByEmail(String email){
        return userRepository.existsUserByEmail(email);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id ){
        return userRepository.findById(id);
    }

    public MessageDefault isInvalid(String token, User user){
        try{
            if(token == null){
                return MessageDefault.UNAUTHORIZED;
            }else{
                if(user != null){
                    if(user.getToken().equals(UUID.fromString(token))){
                        if(user.isSessionValid()){
                            return null;
                        }else{
                            return MessageDefault.INVALID_SESSION;
                        }
                    }else{
                        return MessageDefault.UNAUTHORIZED;
                    }
                }else{
                    return MessageDefault.USER_NOT_FOUND;
                }
            }
        } catch (IllegalArgumentException exception){
            return MessageDefault.INVALID_TOKEN;
        }
    }

    public User create(User user){
         user.generateUser();
         if(!CollectionUtils.isEmpty(user.getPhones())){
             phoneRepository.saveAll(user.getPhones());
         }
         return userRepository.save(user);
    }

    public User update(User user){
        user.updateLastLogin();
        return userRepository.save(user);
    }
}
