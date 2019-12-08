package com.xtorres.restpets.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xtorres.restpets.R;
import com.xtorres.restpets.models.ApiError;
import com.xtorres.restpets.models.User;
import com.xtorres.restpets.services.ApiService;
import com.xtorres.restpets.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText correoInput;
    private EditText contraseñaInput;
    private Button registroButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correoInput = findViewById(R.id.main_input_correo);
        contraseñaInput = findViewById(R.id.main_input_contraseña);
        loginButton = findViewById(R.id.main_button_ingresar);
        registroButton = findViewById(R.id.main_button_registro);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        loadLastUsername();

        verifyLoginStatus();

    }

    private void callRegister(){

        Intent intent = new Intent(this, RegisterUserAcitivity.class);
        startActivity(intent);

    }

    private void login(){

        final String correo = correoInput.getText().toString();
        String password = contraseñaInput.getText().toString();

        if(correo.isEmpty()){
            Toast.makeText(this, "Ingrese el correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<User> call = service.login(correo, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    Log.d(TAG, "usuario" + correo);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    sp.edit()
                            .putString("correo", user.getCorreo())
                            .putString("token", user.getToken())
                            .putBoolean("islogged", true)
                            .commit();

                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();

                    Toast.makeText(MainActivity.this, "Bienvenido"+ user.getNombres(), Toast.LENGTH_SHORT).show();
                }else {
                    ApiError error = ApiServiceGenerator.parseError(response);
                    Toast.makeText(MainActivity.this, "onError: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadLastUsername(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String correo = sp.getString("correo", null);
        if(correo != null){
            correoInput.setText(correo);
        }
    }

    private void verifyLoginStatus(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean islogged = sp.getBoolean("islogged", false);

        if(islogged){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

}
