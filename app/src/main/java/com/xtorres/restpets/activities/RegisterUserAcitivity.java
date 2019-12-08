package com.xtorres.restpets.activities;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xtorres.restpets.R;
import com.xtorres.restpets.models.User;
import com.xtorres.restpets.services.ApiService;
import com.xtorres.restpets.services.ApiServiceGenerator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserAcitivity extends AppCompatActivity {

    private static final String TAG = RegisterUserAcitivity.class.getSimpleName();

    private EditText nombreInput;
    private EditText correoInput;
    private EditText contraseñaInput;
    private Button registroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        nombreInput = findViewById(R.id.register_input_nombre);
        correoInput = findViewById(R.id.register_input_correo);
        contraseñaInput = findViewById(R.id.register_input_contraseña);
        registroButton = findViewById(R.id.register_button_registro);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private Bitmap bitmap;

    private void register() {

        String nombre = nombreInput.getText().toString();
        String correo = correoInput.getText().toString();
        String contraseña = contraseñaInput.getText().toString();

        if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Nombre, correo y contraseña  son campos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<User> call;

        call = service.createUser(nombre, correo, contraseña);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.isSuccessful()) {

                        User user = response.body();
                        Log.d(TAG, "user: " + user);

                        Toast.makeText(RegisterUserAcitivity.this, "Registro guardado satisfactoriamente", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);

                        finish();

                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegisterUserAcitivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(RegisterUserAcitivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}