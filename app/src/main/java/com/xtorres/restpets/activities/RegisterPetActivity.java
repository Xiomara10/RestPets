package com.xtorres.restpets.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.xtorres.restpets.R;

public class RegisterPetActivity extends AppCompatActivity {

    private ImageView foto;
    private EditText nombreInput;
    private EditText razaInput;
    private EditText edadInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);
    }
}
