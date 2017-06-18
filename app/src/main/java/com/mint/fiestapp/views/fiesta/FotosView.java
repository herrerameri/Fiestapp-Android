package com.mint.fiestapp.views.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mint.fiestapp.R;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.services.fotos.IFotosService;
import com.mint.fiestapp.views.custom.ImageSquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class FotosView {

    public interface IFotosViewClickListener{
        void verGaleriaClickCallback();
        void subirFotoClickCallback();
    }

    private View layout;

    //region Controles
    private Button btnVerGaleria;
    private Button btnSubirFoto;
    private LinearLayout linFotosFiesta;
    private LinearLayout linSinFotos;
    private HorizontalScrollView scrConFotos;
    private LinearLayout pgbCargando;
    private Context contexto;
    //endregion

    private IFotosViewClickListener listener;
    public FotosView(IFotosViewClickListener listener){
        this.listener = listener;
    }

    public View getLayout(Context contexto){
        this.contexto = contexto;
        layout = LayoutInflater.from(contexto).inflate(R.layout.activity_fiesta_fotos, null, false);
        binding();
        eventos();
        return layout;
    }

    private void binding(){
        linSinFotos = (LinearLayout) layout.findViewById(R.id.linSinFotos);
        scrConFotos = (HorizontalScrollView) layout.findViewById(R.id.scrConFotos);
        linFotosFiesta = (LinearLayout)layout.findViewById(R.id.linFotosFiesta);
        btnVerGaleria = (Button) layout.findViewById(R.id.btnVerGaleria);
        btnSubirFoto = (Button) layout.findViewById(R.id.btnSubirFoto);
        pgbCargando = (LinearLayout) layout.findViewById(R.id.pgbCargando);
    }

    private void eventos(){
        verGaleria();
        subirFoto();
    }

    private void verGaleria(){
        btnVerGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.verGaleriaClickCallback();
        }});
    }

    private void subirFoto(){
        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.subirFotoClickCallback();
            }
        });
    }

    public void setFotos(List<FotoModel> fotos){
        pgbCargando.setVisibility(View.GONE);
        linFotosFiesta.removeAllViews();

        if(fotos.size() > 0){
            linSinFotos.setVisibility(View.GONE);
            btnVerGaleria.setVisibility(View.VISIBLE);
            scrConFotos.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParamsImagen = obtenerParametrosImagen();
            for (FotoModel foto : fotos) {
                ImageView imgFiesta = new ImageView(contexto);
                imgFiesta.setLayoutParams(layoutParamsImagen);
                Picasso.with(contexto).load(foto.Imagen)
                        .transform(new ImageSquareTransform(contexto.getResources().getDimensionPixelSize(R.dimen.height_foto_fiesta)))
                        .resizeDimen(R.dimen.width_foto_fiesta,R.dimen.height_foto_fiesta)
                        .centerCrop()
                        .into(imgFiesta);

                linFotosFiesta.addView(imgFiesta);
            }
        }
        else{
            linSinFotos.setVisibility(View.VISIBLE);
            scrConFotos.setVisibility(View.GONE);
            btnVerGaleria.setVisibility(View.GONE);
        }
    }

    private LinearLayout.LayoutParams obtenerParametrosImagen(){
        int margin = contexto.getResources().getDimensionPixelSize(R.dimen.margin_foto_fiesta);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                contexto.getResources().getDimensionPixelSize(R.dimen.width_foto_fiesta),
                contexto.getResources().getDimensionPixelSize(R.dimen.height_foto_fiesta));

        layoutParams.setMargins(margin,margin,margin,margin);
        return layoutParams;
    }
}
