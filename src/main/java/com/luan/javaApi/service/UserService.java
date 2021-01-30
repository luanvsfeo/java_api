package com.luan.javaApi.service;

import com.luan.javaApi.domain.User;
import com.luan.javaApi.repository.PhoneRepository;
import com.luan.javaApi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PhoneRepository phoneRepository;

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

    public User create(User user){
         user.generateUser();
         if(!CollectionUtils.isEmpty(user.getPhones())){
             phoneRepository.saveAll(user.getPhones());
         }
         return userRepository.save(user);
    }

    public User update(User user){
        return userRepository.save(user);
    }
}
