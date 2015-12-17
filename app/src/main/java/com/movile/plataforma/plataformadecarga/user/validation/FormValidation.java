package com.movile.plataforma.plataformadecarga.user.validation;

import android.widget.EditText;

/**
 * Created by pollo on 16/12/15.
 */
public class FormValidation {
    //Return true if input value is empty
    public static boolean isEmpty(EditText input){
        return ("").equals(input.getText().toString().trim());
    }

    public static boolean notEquals(EditText input1, EditText input2){
        return !(input1.getText().toString()).equals(input2.getText().toString().trim());
    }
}
