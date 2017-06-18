package com.mint.fiestapp.views.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.Facebook;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.mint.fiestapp.views.custom.ImageSquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MeGustasView {

    public interface IMeGustasViewClickListener{
        void quienGustaClickCallback();
    }

    private View layout;

    //region Controles
    private Button btnQuienGusta;
    private LinearLayout linInvitadosFiesta;
    private LinearLayout linSinInvitados;
    private LinearLayout pgbCargando;
    private LinearLayout linFooterMeGustas;
    private Context contexto;
    //endregion

    private IMeGustasViewClickListener listener;
    public MeGustasView(IMeGustasViewClickListener listener){
        this.listener = listener;
    }

    public View getLayout(Context contexto, List<String> invitados){
        this.contexto = contexto;
        layout = LayoutInflater.from(contexto).inflate(R.layout.activity_fiesta_me_gustas, null, false);
        binding();
        eventos();
        setInvitados(invitados);
        return layout;
    }

    private void binding(){
        linSinInvitados = (LinearLayout) layout.findViewById(R.id.linSinInvitados);
        linInvitadosFiesta = (LinearLayout)layout.findViewById(R.id.linInvitadosFiesta);
        linFooterMeGustas = (LinearLayout)layout.findViewById(R.id.linFooterMeGustas);
        btnQuienGusta = (Button) layout.findViewById(R.id.btnQuienGusta);
        pgbCargando = (LinearLayout) layout.findViewById(R.id.pgbCargando);
    }

    private void eventos(){
    }

    private void setInvitados(List<String> invitados){
        pgbCargando.setVisibility(View.GONE);
        linInvitadosFiesta.removeAllViews();

        if(invitados != null && invitados.size() > 0){
            linSinInvitados.setVisibility(View.GONE);
            linFooterMeGustas.setVisibility(View.VISIBLE);
            linInvitadosFiesta.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams layoutParamsImagen = obtenerParametrosImagen();
            for (String idUsuario : invitados) {
                ImageView imgFiesta = new ImageView(contexto);
                imgFiesta.setLayoutParams(layoutParamsImagen);
                Picasso.with(contexto).load(Facebook.getFotoPerfil(idUsuario))
                        .transform(new ImageCircleTransform())
                        .resizeDimen(R.dimen.width_foto_fiesta,R.dimen.height_foto_fiesta)
                        .into(imgFiesta);
                linInvitadosFiesta.addView(imgFiesta);
            }
        }
        else{
            linSinInvitados.setVisibility(View.VISIBLE);
            linInvitadosFiesta.setVisibility(View.GONE);
            linFooterMeGustas.setVisibility(View.GONE);
        }
    }

    private LinearLayout.LayoutParams obtenerParametrosImagen(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                contexto.getResources().getDimensionPixelSize(R.dimen.width_foto_invitado),
                contexto.getResources().getDimensionPixelSize(R.dimen.height_foto_fiesta));

        return layoutParams;
    }
}
