package com.movile.plataforma.plataformadecarga.restconnector;

import android.os.AsyncTask;

import com.movile.plataforma.plataformadecarga.config.AppProperties;
import com.movile.plataforma.plataformadecarga.user.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by pollo on 17/12/15.
 */
public class LoginAsyncTask extends AsyncTask<Object, Object, String> {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON_APP_HEADER = "application/json";
    private static final String POST = "POST";
    private static final String GET = "GET";
    public static final String OK = "200";
    public static final String FAIL = "400";
    private static final String LOGIN_QUERY = "/login";
    private static final String PROTOCOL_PROPERTY = "api.protocol";
    private static final String ENDPOINT_PROPERTY = "api.endpoint";

    private String endpoint;
    private String protocol;
    private LoginActivity delegate;
    private HttpURLConnection connection;

    public LoginAsyncTask(LoginActivity delegate){
        this.delegate = delegate;
        AppProperties appProps = AppProperties.getInstance(delegate);
        this.protocol = appProps.getProperties().getProperty(PROTOCOL_PROPERTY);
        this.endpoint = appProps.getProperties().getProperty(ENDPOINT_PROPERTY);

    }

    public void login(String username, String password){
        String query = null;
            query = LOGIN_QUERY + "?user=" + username;

        Object[] params = new Object[]{query, null, GET};
        this.execute(params);
    }

    @Override
    protected String doInBackground(Object... params) {
        String query = (String) params[0];
        String method = (String) params[2];
        try {
            //TODO consultar por el método POST o GET
            URL url;
            String urlStr = this.protocol +"://" + this.endpoint + query;
            System.out.println("Url: " +urlStr);
            url = new URL(urlStr);
            this.connection = (HttpURLConnection) url.openConnection();

            StringBuilder builder = new StringBuilder();
            int status = connection.getResponseCode();
            if(status == 200) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String aux = "";
                while ((aux = in.readLine()) != null) {
                    builder.append(aux);
                }

                in.close();
            }

            return builder.toString();

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
        return FAIL;
    }

    public void onPostExecute(String result){
        this.delegate.loginFnished(result);
    }
}
