package com.movile.plataforma.plataformadecarga.restconnector;

import android.os.AsyncTask;

import com.movile.plataforma.plataformadecarga.config.AppProperties;
import com.movile.plataforma.plataformadecarga.user.LoginActivity;
import com.movile.plataforma.plataformadecarga.user.SingupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by pollo on 17/12/15.
 */
public class SingupAsyncTask extends AsyncTask<Object, Object, String> {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON_APP_HEADER = "application/json";
    private static final String POST = "POST";
    private static final String GET = "GET";
    public static final String OK = "200";
    public static final String FAIL = "400";
    private static final String SINGUP_QUERY = "/singup";
    private static final String PROTOCOL_PROPERTY = "api.protocol";
    private static final String ENDPOINT_PROPERTY = "api.endpoint";

    private String endpoint;
    private String protocol;
    private SingupActivity delegate;
    private HttpURLConnection connection;

    public SingupAsyncTask(SingupActivity delegate){
        this.delegate = delegate;
        AppProperties appProps = AppProperties.getInstance(delegate);
        this.protocol = appProps.getProperties().getProperty(PROTOCOL_PROPERTY);
        this.endpoint = appProps.getProperties().getProperty(ENDPOINT_PROPERTY);

    }

    public void singup(String username, String password, String type){
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object[] params = new Object[]{SINGUP_QUERY, json, GET};
        this.execute(params);
    }

    @Override
    protected String doInBackground(Object... params) {
        String query = (String) params[0];
        String method = (String) params[2];
        JSONObject json = (JSONObject) params[1];

        try {
            //TODO consultar por el método POST o GET
            URL url;
            String urlStr = this.protocol +"://" + this.endpoint + query;
            System.out.println("Url: " +urlStr);
            url = new URL(urlStr);
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setDoInput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestProperty(CONTENT_TYPE, JSON_APP_HEADER);
            this.connection.setRequestMethod(POST);
            this.connection.connect();
            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            printout.writeUTF(URLEncoder.encode(json.toString(), "UTF-8"));
            printout.flush();
            printout.close();

        }
        catch (MalformedURLException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return OK;
    }

    public void onPostExecute(String result){
         this.delegate.singupFnished(result);
    }
}
