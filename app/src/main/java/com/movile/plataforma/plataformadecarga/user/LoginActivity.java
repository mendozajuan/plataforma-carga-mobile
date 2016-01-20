package com.movile.plataforma.plataformadecarga.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.movile.plataforma.plataformadecarga.R;

import com.movile.plataforma.plataformadecarga.restconnector.LoginAsyncTask;
import com.movile.plataforma.plataformadecarga.user.validation.FormValidation;
import com.movile.plataforma.plataformadecarga.user.consumer.search.UserConsumerSearch;

public class LoginActivity extends AppCompatActivity {

    private LoginAsyncTask loginAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.loginAsyncTask = new LoginAsyncTask(this);
    }
    public void sendLoginForm(View view) {
        EditText userName = (EditText) findViewById(R.id.loginUserNameTxt);
        EditText password = (EditText) findViewById(R.id.loginPassTxt);

        if(FormValidation.isEmpty(userName)){
            userName.setError("Requerido");
        }
        else if(FormValidation.isEmpty(password)) {
            password.setError("Requerido");
        }
        else{
            this.loginAsyncTask.login(userName.getText().toString(), password.getText().toString());

        }
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, UserConsumerSearch.class);
        startActivity(intent);
    }

    public void loginFnished(String result){
        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
