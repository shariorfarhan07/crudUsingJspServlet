package com.minitwitter.service;

import com.minitwitter.dao.userDao;
import com.minitwitter.dto.Credentials;
//import com.minitwitter.dto.user;
import com.minitwitter.dto.userDto;

public class AuthService {
    public boolean isValidCredential(Credentials user) {
        userDto userCred=userDao.searchUser(user.getUserName());
        if (userCred.getUserName() == null) return false;
        if ( userCred.getPassword().equals( user.getPassword())){
            return true;
        }
        return false;

    }
}
