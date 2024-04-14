package com.minitwitter.service;

import com.minitwitter.dao.FollowerMappingDao;

public class FollowerService {

    private  static FollowerService followerService;

    private  FollowerMappingDao followerMappingDao;

    private FollowerService(){
        followerMappingDao = FollowerMappingDao.getInstance();
    }

    public static synchronized FollowerService getFollowerService(){
        if (followerService == null){
            followerService=new FollowerService();
        }
        return followerService;
    }


    public  void followUser(int userid, int follow) {
        followerMappingDao.insertFollower(userid, follow);
        System.out.println("User "+userid+" added "+follow);
    }

    public  void removeFollower(int userid, int unfollow) {
        followerMappingDao.removeFollower(userid,unfollow);
        System.out.println("User "+userid+" removed "+unfollow);
    }
}
