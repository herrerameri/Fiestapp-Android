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
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.views.custom.CustomTextView;
import com.mint.fiestapp.views.custom.ExpandibleTextView;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.FotoViewHolder> {

    List<FotoModel> datos;
    FotosAdapter.OnClickListener listener;
    static Context context;

    public interface OnClickListener {
        void onFotoClick(Context context, FotoModel item);
        void onReaccionClick(FotoModel item);
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgFoto) ImageView imgFoto;
        @BindView(R.id.imgUsuario) ImageView imgUsuario;
        @BindView(R.id.imgReaccion) ImageView imgReaccion;
        @BindView(R.id.texNombreUsuario) CustomTextView texNombreUsuario;
        @BindView(R.id.texDescripcion) ExpandibleTextView texDescripcion;
        @BindView(R.id.texReacciones) CustomTextView texReacciones;
        @BindView(R.id.texDetalle) CustomTextView texDetalle;

        public FotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final FotoModel item, final FotosAdapter.OnClickListener  listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onFotoClick(context, item);
                }
            });
        }
    }

    public FotosAdapter(List<FotoModel> datos, Context context, FotosAdapter.OnClickListener listener) {
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
    public void onBindViewHolder(final FotoViewHolder view, int position) {
        final FotoModel item = datos.get(position);
        Picasso.with(context).load(item.Imagen).fit().centerCrop().into(view.imgFoto);
        view.texNombreUsuario.setText(item.Usuario.Nombre);
        view.texDescripcion.setText(item.Descripcion);
        view.texReacciones.setText(item.Reacciones());
        view.texDetalle.setText(item.FechaHora());
        if(item.YoReaccione()){
            view.imgReaccion.setColorFilter(context.getResources().getColor(R.color.fucsia));
        }
        else{
            view.imgReaccion.clearColorFilter();
        }
        Picasso.with(context).load(Facebook.getFotoPerfil(item.Usuario.Id)).transform(new ImageCircleTransform()).into(view.imgUsuario);
        view.bind(item, listener);
        view.imgReaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.onReaccionClick(item);
            }
        });
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

    public void addReaccionFoto(String keyFoto, HashMap<String, UsuarioModel> nuevaReaccion){
        for (int i =0 ; i<datos.size(); i++) {
            if(datos.get(i).key.equals(keyFoto))
            {
                if(datos.get(i).Reacciones == null){
                    datos.get(i).Reacciones = new HashMap<>();
                }
                datos.get(i).Reacciones.putAll(nuevaReaccion);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void removeReaccionFoto(String keyFoto, String keyReaccion){
        for (int i =0 ; i<datos.size(); i++) {
            if(datos.get(i).key.equals(keyFoto))
            {
                if(datos.get(i).Reacciones != null){
                    datos.get(i).Reacciones.remove(keyReaccion);
                }
                break;
            }
        }
        notifyDataSetChanged();
    }

}