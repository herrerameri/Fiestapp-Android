package com.mint.fiestapp.views.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mint.fiestapp.R;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.views.custom.ImageSquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MusicaView {

    private View layout;

    //region Controles
    private Button btnProponer;
    private LinearLayout linNoOpinar;
    private LinearLayout linOpinar;
    private Context contexto;
    //endregion

    public View getLayout(Context contexto, boolean opinar){
        this.contexto = contexto;
        layout = LayoutInflater.from(contexto).inflate(R.layout.activity_fiesta_musica, null, false);
        binding();
        eventos();
        if(opinar)
        {
            showOpinar();
        }
        return layout;
    }

    private void binding(){
        btnProponer = (Button) layout.findViewById(R.id.btnProponer);
        linNoOpinar = (LinearLayout) layout.findViewById(R.id.linNoOpinar);
        linOpinar = (LinearLayout) layout.findViewById(R.id.linOpinar);
    }

    private void eventos(){
    }

    public void showOpinar(){
        linNoOpinar.setVisibility(View.GONE);
        linOpinar.setVisibility(View.VISIBLE);
    }

    public void setOpinion(int valor){

    }
}

