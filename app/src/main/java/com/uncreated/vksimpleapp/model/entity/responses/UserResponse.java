package com.uncreated.vksimpleapp.model.entity.responses;

import com.uncreated.vksimpleapp.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserResponse extends BaseResponse {

    private ArrayList<User> response;

    public List<User> getUsers() {
        return response;
    }
}
