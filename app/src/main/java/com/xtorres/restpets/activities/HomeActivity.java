package com.xtorres.restpets.activities;

import android.speech.RecognitionListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.xtorres.restpets.R;
import com.xtorres.restpets.adapters.PetsAdapter;
import com.xtorres.restpets.models.Pet;
import com.xtorres.restpets.services.ApiService;
import com.xtorres.restpets.services.ApiServiceGenerator;

import java.security.KeyStore;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private RecyclerView petsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        petsList = findViewById(R.id.recyclerview);
        petsList.setLayoutManager(new LinearLayoutManager(this));

        petsList.setAdapter(new PetsAdapter());

        initialize();

    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        service.getPet().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pet>> call, @NonNull Response<List<Pet>> response) {
                try{
                    if(response.isSuccessful()){
                        List<Pet> pets = response.body();
                        Log.d(TAG, "pets: "+pets);

                        PetsAdapter adapter = (PetsAdapter) petsList.getAdapter();
                        adapter.setPets(pets);
                        adapter.notifyDataSetChanged();
                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                }catch (Throwable t){
                    Log.e(TAG, "onThrowable: "+t.getMessage(),t);
                    Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pet>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure"+ t.getMessage(), t);
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
