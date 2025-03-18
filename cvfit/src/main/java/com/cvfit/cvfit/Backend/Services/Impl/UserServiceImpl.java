package com.cvfit.cvfit.Backend.Services.Impl;



import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.Services.UserService;
import com.cvfit.cvfit.Backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    UserRepository userrepository ;

    public UserServiceImpl(UserRepository userrepository ) {
        this.userrepository =  userrepository ;
    }

    @Override
    public String addUser(User user) {
        userrepository.save(user) ;
        return "created Successfully";
    }

    @Override
    public String updateUser(User user) {
        userrepository.save(user) ;
        return "User was updated successfully ";
    }

    @Override
    public String deleteUser(Long userId) {
        userrepository.deleteById(userId) ;
        return "User was deleted successfully ";
    }

    @Override
    public User getUser(Long userId) {
        return userrepository.findById(userId).get();
    }

    @Override
    public List<User> getAllUsers() {

        return userrepository.findAll();
    }
}
