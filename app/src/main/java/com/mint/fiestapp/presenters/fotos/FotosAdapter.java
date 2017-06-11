package com.mint.fiestapp.presenters.fotos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.Facebook;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.views.custom.CustomTextView;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.FotoViewHolder> {

    List<FotoModel> datos;
    FotosAdapter.OnFotoClickListener listener;
    static Context context;

    public interface OnFotoClickListener {
        void onFotoClick(Context context, FotoModel item);
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgFoto) ImageView imgFoto;
        @BindView(R.id.imgUsuario) ImageView imgUsuario;
        @BindView(R.id.imgReaccion) ImageView imgReaccion;
        @BindView(R.id.texNombreUsuario) CustomTextView texNombreUsuario;
        @BindView(R.id.texReacciones) CustomTextView texReacciones;
        @BindView(R.id.texDetalle) CustomTextView texDetalle;

        public FotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final FotoModel item, final FotosAdapter.OnFotoClickListener  listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onFotoClick(context, item);
                }
            });
        }
    }

    public FotosAdapter(List<FotoModel> datos, Context context, FotosAdapter.OnFotoClickListener listener) {
        this.datos = datos;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public FotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fotos_item, parent, false);
        FotoViewHolder vh = new FotoViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(FotoViewHolder view, int position) {
        FotoModel item = datos.get(position);
        Picasso.with(context).load(item.Imagen).into(view.imgFoto);
        view.texNombreUsuario.setText(item.Usuario.Nombre);
        view.texReacciones.setText(item.Reacciones());
        view.texDetalle.setText(item.FechaHora());
        if(item.YoReaccione()){
            view.imgReaccion.setColorFilter(context.getResources().getColor(R.color.fucsia));
        }
        Picasso.with(context).load(Facebook.getFotoPerfil(item.Usuario.Id)).transform(new ImageCircleTransform()).into(view.imgUsuario);
        view.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void addDatos(List<FotoModel> fotos) {
        this.datos.addAll(fotos);
        notifyDataSetChanged();
    }

    public void setUsuario(String Usuario, String nombreUsuario){
        for (FotoModel foto : datos) {
            if(foto.Usuario.Id.equals(Usuario)){
                foto.Usuario.Nombre = nombreUsuario;
            }
        }
        notifyDataSetChanged();
    }
}