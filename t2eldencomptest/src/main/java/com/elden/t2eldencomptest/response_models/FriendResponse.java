package com.elden.t2eldencomptest.response_models;

import com.elden.t2eldencomptest.model.Friend;

import java.util.List;

/**
 * Created by elden_000 on 4/14/2017.
 */

public class FriendResponse {
    List<Friend> friends;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
