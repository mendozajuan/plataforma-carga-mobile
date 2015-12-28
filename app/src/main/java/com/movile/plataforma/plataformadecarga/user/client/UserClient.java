package com.movile.plataforma.plataformadecarga.user.client;

import android.content.Context;

import com.movile.plataforma.plataformadecarga.config.AppProperties;
import com.movile.plataforma.plataformadecarga.restconnector.RestConnector;

import org.json.JSONObject;

import java.util.Properties;

/**
 * Created by pollo on 17/12/15.
 */
public class UserClient {
    private static final String PROTOCOL_PROPERTY = "api.protocol";
    private static final String ENDPOINT_PROPERTY = "api.endpoint";

    //Querys
    private static final String LOGIN_QUERY = "/login";
    private Context context;
    private RestConnector restConnector;

    public  UserClient(Context context){
        AppProperties appProps = AppProperties.getInstance(context);
        String protocol = appProps.getProperties().getProperty(PROTOCOL_PROPERTY);
        String endpoint = appProps.getProperties().getProperty(ENDPOINT_PROPERTY);
        this.restConnector = new RestConnector(protocol, endpoint);
    }

    public int login(JSONObject loginRequest){
        return this.restConnector.postJson(LOGIN_QUERY, loginRequest);
    }

}
