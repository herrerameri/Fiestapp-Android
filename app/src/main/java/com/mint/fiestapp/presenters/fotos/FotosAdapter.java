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

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.FotoViewHolder> {

    List<FotoModel> datos;
    FotosAdapter.OnFotoClickListener listener;
    static Context context;

    public interface OnFotoClickListener {
        void onFotoClick(Context context, FotoModel item);
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFoto;
        public ImageView imgUsuario;
        public ImageView imgReaccion;
        public CustomTextView texNombreUsuario;
        public CustomTextView texReacciones;
        public CustomTextView texDetalle;

        public FotoViewHolder(View view) {
            super(view);
            imgFoto = (ImageView) view.findViewById(R.id.imgFoto);
            imgUsuario = (ImageView) view.findViewById(R.id.imgUsuario);
            imgReaccion = (ImageView) view.findViewById(R.id.imgReaccion);
            texReacciones = (CustomTextView) view.findViewById(R.id.texReacciones);
            texNombreUsuario = (CustomTextView) view.findViewById(R.id.texNombreUsuario);
            texDetalle = (CustomTextView) view.findViewById(R.id.texDetalle);
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
            Picasso.with(context).load(R.mipmap.ic_room).into(view.imgReaccion);
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