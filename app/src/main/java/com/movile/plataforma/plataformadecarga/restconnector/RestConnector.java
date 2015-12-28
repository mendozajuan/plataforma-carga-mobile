package com.movile.plataforma.plataformadecarga.restconnector;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by pollo on 17/12/15.
 */
public class RestConnector {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON_APP_HEADER = "application/json";
    public static final int OK = 200;
    public static final int FAIL = 400;
    private String endpoint;
    private String protocol;


    public RestConnector(String protocol, String endpoint){
        this.endpoint = endpoint;
        this.protocol = protocol;

    }
    public int postJson(String query, JSONObject json){
        try {
            URLConnection connection = this.getPostConnection(query);
            connection.connect();
            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            printout.writeUTF(URLEncoder.encode(json.toString(), "UTF-8"));
            printout.flush();
            printout.close();
            return OK;
        }
        catch (MalformedURLException e){

        }
        catch (IOException e){

        }
        return FAIL;
    }

    private URLConnection getPostConnection(String query) throws MalformedURLException, IOException{
        URLConnection connection;
        URL url;
        String urlStr = this.protocol + this.endpoint + query;
        url = new URL(urlStr);
        connection = url.openConnection();
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty(CONTENT_TYPE, JSON_APP_HEADER);
        connection.setRequestProperty("Host", this.endpoint);
        return connection;

    }


}
