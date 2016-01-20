package com.movile.plataforma.plataformadecarga.user.service.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pollo on 17/12/15.
 */
public class UserServiceRequestBuilder {

    public static JSONObject buildLoginRequest(String username, String password, String type){
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
