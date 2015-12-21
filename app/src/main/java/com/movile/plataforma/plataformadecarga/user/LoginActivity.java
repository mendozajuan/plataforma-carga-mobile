package com.movile.plataforma.plataformadecarga.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.movile.plataforma.plataformadecarga.R;
import com.movile.plataforma.plataformadecarga.user.consumer.search.UserConsumerSearch;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, UserConsumerSearch.class);
        startActivity(intent);
    }
}
