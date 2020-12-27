package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();

    void addUser(User user);

    void setUserPwd(User user, String pwd);

    void updateUser(User user);

    void deleteUser(User user);

    void activateUser(String userId, String code) throws Exception;

    User getByName(String name);

    User getByEmail(String email);
}