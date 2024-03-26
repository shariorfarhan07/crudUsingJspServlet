package com.minitwitter.service;

import com.minitwitter.dao.userDao;
import com.minitwitter.dto.Credentials;
import com.minitwitter.dto.user;

public class AuthService {
    public boolean isValidCredential(Credentials user) {
        userDao database=new userDao();
        user userCred=database.searchUser(user.getUserName());
        if (userCred.getPassword().equals( user.getPassword())){
            return true;
        }
        return false;

    }
}
