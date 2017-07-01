package com.mint.fiestapp.presenters.fotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        void onCompartirClick(Uri bitmapUri);
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgFoto) ImageView imgFoto;
        @BindView(R.id.imgUsuario) ImageView imgUsuario;
        @BindView(R.id.imgReaccion) ImageView imgReaccion;
        @BindView(R.id.imgCompartir) ImageView imgCompartir;
        @BindView(R.id.texNombreUsuario) CustomTextView texNombreUsuario;
        @BindView(R.id.texDescripcion) CustomTextView texDescripcion;
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
        view.imgCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCompartirClick(getLocalBitmapUri(view.imgFoto));
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
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void removeReaccionFoto(String keyFoto, String keyReaccion){
        for (int i =0 ; i<datos.size(); i++) {
            if(datos.get(i).key.equals(keyFoto))
            {
                if(datos.get(i).Reacciones != null){
                    datos.get(i).Reacciones.remove(keyReaccion);
                }
                notifyItemChanged(i);
                break;
            }
        }
    }

    private Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "Fiestapp_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 20, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}