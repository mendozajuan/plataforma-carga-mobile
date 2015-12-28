package com.movile.plataforma.plataformadecarga.config;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by pollo on 17/12/15.
 */
public class AppProperties {
    private static AppProperties instance;
    private Properties props;

    public static AppProperties getInstance(Context context) {
        if(instance == null){
            instance = new AppProperties(context);
        }
        return instance;
    }

    private AppProperties(Context context) {
        this.props = new java.util.Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("environment.properties"), "UTF8");
            props.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties(){
        return this.props;
    }
}
