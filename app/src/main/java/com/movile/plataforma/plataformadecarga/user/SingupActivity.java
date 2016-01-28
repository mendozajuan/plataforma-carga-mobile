package com.movile.plataforma.plataformadecarga.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.movile.plataforma.plataformadecarga.R;
import com.movile.plataforma.plataformadecarga.restconnector.SingupAsyncTask;
import com.movile.plataforma.plataformadecarga.user.validation.FormValidation;

/**
 * Created by pollo on 15/12/15.
 */
public class SingupActivity  extends AppCompatActivity {

    private SingupAsyncTask singupAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.singupAsyncTask = new SingupAsyncTask(this);
        setContentView(R.layout.activity_singup);
        Spinner spinner = (Spinner) findViewById(R.id.userTypesSpn);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void sendSingupForm(View view) {
        EditText userName = (EditText) findViewById(R.id.singupUserNameTxt);
        EditText password = (EditText) findViewById(R.id.singupPassTxt);
        EditText rePassword = (EditText) findViewById(R.id.singupRepassTxt);
        Spinner userType = (Spinner) findViewById(R.id.userTypesSpn);
        if(FormValidation.isEmpty(userName)){
            userName.setError("Requerido");
        }
        else if(FormValidation.isEmpty(password)){
            password.setError("Requerido");
        }
        else if(FormValidation.notEquals(password, rePassword)){
            rePassword.setError("Las contraseñas no coinciden");
        }
        else{
            this.singupAsyncTask.singup(userName.getText().toString(), password.getText().toString(), userType.toString());
            Toast.makeText(SingupActivity.this, "Se ha registrado un usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void singupFnished(String result){
        Toast.makeText(SingupActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
