package com.mint.fiestapp.presenters.home;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.views.custom.CustomTextView;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MisFiestasAdapter extends RecyclerView.Adapter<MisFiestasAdapter.FiestaViewHolder> {
    List<FiestaModel> datos;
    MisFiestasAdapter.OnFiestaClickListener listener;
    static Context context;

    public interface OnFiestaClickListener {
        void onFiestaClick(Context context, FiestaModel item);
    }

    public static class FiestaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.texNombre) CustomTextView texNombre;
        @BindView(R.id.texDetalle)  CustomTextView texDetalle;
        @BindView(R.id.imgFiesta)  ImageView imgFiesta;

        public FiestaViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final FiestaModel item, final MisFiestasAdapter.OnFiestaClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onFiestaClick(context, item);
                }
            });
        }
    }

    public MisFiestasAdapter(List<FiestaModel> datos, Context context, MisFiestasAdapter.OnFiestaClickListener listener) {
        this.datos = datos;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public FiestaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fiesta, parent, false);
        FiestaViewHolder vh = new FiestaViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(FiestaViewHolder view, int position) {
        view.texNombre.setText(datos.get(position).Nombre);
        view.texDetalle.setText(datos.get(position).FechaHora());
        Picasso.with(context).load(datos.get(position).Imagen)
                .transform(new ImageCircleTransform())
                .into(view.imgFiesta);
        view.bind(datos.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}
