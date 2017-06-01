package com.mint.fiestapp.presenters.fotos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mint.fiestapp.R;
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
        public CustomTextView texNombreUsuario;
        public CustomTextView texDetalle;

        public FotoViewHolder(View view) {
            super(view);
            imgFoto = (ImageView) view.findViewById(R.id.imgFoto);
            imgUsuario = (ImageView) view.findViewById(R.id.imgUsuario);
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
        Picasso.with(context).load(datos.get(position).Imagen).into(view.imgFoto);
        view.texDetalle.setText(datos.get(position).Descripcion);
        Picasso.with(context).load("https://graph.facebook.com/"+datos.get(position).Usuario+"/picture?type=large&width=1080")
                .transform(new ImageCircleTransform()).into(view.imgUsuario);

        view.bind(datos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setDatos(ArrayList<FotoModel> mGridData) {
        this.datos = mGridData;
        notifyDataSetChanged();
    }
}