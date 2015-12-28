package com.movile.plataforma.plataformadecarga.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.movile.plataforma.plataformadecarga.R;
import com.movile.plataforma.plataformadecarga.user.service.UserService;
import com.movile.plataforma.plataformadecarga.user.validation.FormValidation;

public class LoginActivity extends AppCompatActivity {
    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.userService = new UserService(this);
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
            boolean result = this.userService.login(userName.getText().toString(), password.getText().toString());
            Toast.makeText(LoginActivity.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
        }
    }
}
