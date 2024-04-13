package com.minitwitter.service;

import com.minitwitter.dao.FollowerMappingDao;

public class FollowerService {
    public static void followUser(int userid, int follow) {
        FollowerMappingDao.insertFollower(userid, follow);
        System.out.println("User "+userid+" added "+follow);
    }

    public static void removeFollower(int userid, int unfollow) {
        FollowerMappingDao.removeFollower(userid,unfollow);
        System.out.println("User "+userid+" removed "+unfollow);
    }
}
