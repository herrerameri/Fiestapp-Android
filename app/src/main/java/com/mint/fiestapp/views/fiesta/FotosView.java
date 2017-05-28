package com.mint.fiestapp.views.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.views.custom.ImageSquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FotosView {

    private View layout;
    private Button btnVerGaleria;
    private LinearLayout linFotosFiesta;
    private LinearLayout linSinFotos;
    private HorizontalScrollView scrConFotos;
    private LinearLayout pgbCargando;
    private Context contexto;

    public View getLayout(Context contexto){
        this.contexto = contexto;
        layout = LayoutInflater.from(contexto).inflate(R.layout.activity_fiesta_fotos, null, false);
        linSinFotos = (LinearLayout) layout.findViewById(R.id.linSinFotos);
        scrConFotos = (HorizontalScrollView) layout.findViewById(R.id.scrConFotos);
        linFotosFiesta = (LinearLayout)layout.findViewById(R.id.linFotosFiesta);
        btnVerGaleria = (Button) layout.findViewById(R.id.btnVerGaleria);
        pgbCargando = (LinearLayout) layout.findViewById(R.id.pgbCargando);
        return layout;
    }

    public void setFotos(List<FotoModel> fotos){
        pgbCargando.setVisibility(View.GONE);
        linFotosFiesta.removeAllViews();

        if(fotos.size() > 0){
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
