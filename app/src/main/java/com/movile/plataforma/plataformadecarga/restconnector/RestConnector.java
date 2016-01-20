package com.movile.plataforma.plataformadecarga.restconnector;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by pollo on 17/12/15.
 */
public class RestConnector extends AsyncTask<Object, Object, String> {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON_APP_HEADER = "application/json";
    private static final String POST = "POST";
    private static final String GET = "GET";
    public static final String OK = "200";
    public static final String FAIL = "400";

    private String endpoint;
    private String protocol;
    private RestConsumer delegate;
    private HttpURLConnection connection;
    private RestCallback callback;

    public RestConnector(RestConsumer delegate, String protocol, String endpoint){
        this.endpoint = endpoint;
        this.protocol = protocol;
        this.delegate = delegate;

    }
    public String post(String query, JSONObject json){
        Object[] params = new Object[]{query, json, POST};
        this.execute(params);
        return "";
    }

    public void get(String query, RestCallback callback){
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


            //this.connection.setRequestProperty("Host", this.endpoint);

            if(POST.equals(method)) {
                JSONObject json = (JSONObject) params[1];
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
            else if(GET.equals(method)){

                String f = "";
                int status = connection.getResponseCode();
                if(status == 200) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    f = in.readLine();
                    in.close();
                }

                return f;
            }


            return OK;
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

}
