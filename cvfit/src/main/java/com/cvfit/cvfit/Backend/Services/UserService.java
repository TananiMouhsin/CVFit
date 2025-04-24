package com.cvfit.cvfit.Backend.Services;
import com.cvfit.cvfit.Backend.Entities.User;
import java.util.List;

public interface UserService {
    public String addUser(User user);
    public String updateUser(User user);
    public String deleteUser(Long userId);
    public User getUser(Long userId);
    public List<User> getAllUsers();
    public User getCurrentUser();

}
