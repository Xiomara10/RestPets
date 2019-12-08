package com.xtorres.restpets.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtorres.restpets.R;
import com.xtorres.restpets.models.Pet;
import com.xtorres.restpets.services.ApiService;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder>{

    private static final String TAG = PetsAdapter.class.getSimpleName();

    private List<Pet> pets;

    public PetsAdapter(){
        this.pets = new ArrayList<>();
    }

    public void setPets(List<Pet> pets){
        this.pets = pets;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;
        TextView razaText;
        TextView edadText;

        ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.item_foto);
            nombreText = itemView.findViewById(R.id.item_nombre);
            razaText = itemView.findViewById(R.id.item_raza);
            edadText = itemView.findViewById(R.id.item_edad);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();

        Pet pet = this.pets.get(position);

        viewHolder.nombreText.setText(pet.getNombre());

        String url = ApiService.API_BASE_URL + "/pets/images/" + pet.getFoto();
        Picasso.with(context).load(url).into(viewHolder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return this.pets.size();
    }
}
