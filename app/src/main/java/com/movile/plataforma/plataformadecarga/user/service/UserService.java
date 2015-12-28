package com.movile.plataforma.plataformadecarga.user.service;

import android.content.Context;

import com.movile.plataforma.plataformadecarga.restconnector.RestConnector;
import com.movile.plataforma.plataformadecarga.user.client.UserClient;
import com.movile.plataforma.plataformadecarga.user.service.dto.UserServiceRequestBuilder;

import org.json.JSONObject;

/**
 * Created by pollo on 17/12/15.
 */
public class UserService {
    private UserClient client;

    public UserService(Context context){
        this.client = new UserClient(context);
    }

    public boolean login(String username, String password){
        JSONObject loginRequest = UserServiceRequestBuilder.buildLoginRequest(username, password);
        int response = this.client.login(loginRequest);
        return response == RestConnector.OK;
    }
}
