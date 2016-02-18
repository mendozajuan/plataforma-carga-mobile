package com.movile.plataforma.plataformadecarga.restconnector;

import android.os.AsyncTask;

import com.movile.plataforma.plataformadecarga.config.AppProperties;
import com.movile.plataforma.plataformadecarga.user.LoginActivity;
import com.movile.plataforma.plataformadecarga.user.SingupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
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
    private final String USER_AGENT = "Mozilla/5.0";

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
            json.put("type", "pepe");

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
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(URLEncoder.encode(json.toString(), "UTF-8"));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

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
