package com.mint.fiestapp.presenters.misfiestas;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.views.controls.CustomTextView;
import com.mint.fiestapp.views.controls.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MisFiestasAdapter extends RecyclerView.Adapter<MisFiestasAdapter.ViewHolder> {
    List<FiestaModel> datos;
    MisFiestasAdapter.OnFiestaClickListener listener;
    Context context;

    public interface OnFiestaClickListener {
        void onFiestaClick(FiestaModel item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CustomTextView texNombre;
        public CustomTextView texDetalle;
        public ImageView imgFiesta;

        public ViewHolder(View view) {
            super(view);
            texNombre = (CustomTextView) view.findViewById(R.id.texNombre);
            texDetalle = (CustomTextView) view.findViewById(R.id.texDetalle);
            imgFiesta = (ImageView) view.findViewById(R.id.imgFiesta);
        }

        public void bind(final FiestaModel item, final MisFiestasAdapter.OnFiestaClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onFiestaClick(item);
                }
            });
        }
    }

    public MisFiestasAdapter(List<FiestaModel> datos, MisFiestasAdapter.OnFiestaClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @Override
    public MisFiestasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fiesta, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder view, int position) {
        view.texNombre.setText(datos.get(position).Nombre);
        view.texDetalle.setText(datos.get(position).FechaHora());
        Picasso.with(context).load(datos.get(position).Imagen)
                .transform(new RoundedTransformation(4,0)).into(view.imgFiesta);
        view.bind(datos.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}
