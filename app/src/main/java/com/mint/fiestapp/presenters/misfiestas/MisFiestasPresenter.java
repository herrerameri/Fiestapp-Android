package com.mint.fiestapp.presenters.misfiestas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.misfiestas.IMisFiestasModel;
import com.mint.fiestapp.models.misfiestas.MisFiestasModel;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.misfiestas.IMisFiestasActivity;
import com.mint.fiestapp.views.misfiestas.MisFiestasActivity;

import java.io.Serializable;

public class MisFiestasPresenter implements IMisFiestasPresenter {

    IMisFiestasModel model = new MisFiestasModel();
    IMisFiestasActivity activity;
    Context contexto;

    public void iniciarActivity(Context context){
        Intent intent = new Intent(context, MisFiestasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void setContext(Context context){
        contexto = context;
    }

    public void setActivity(IActivity vista){
        activity = (IMisFiestasActivity)vista;
    }

    public void obtenerFiestas(){
        RespuestaLista<FiestaModel> respuesta = model.obtenerFiestas();

        if(!respuesta.Exito)
        {
            activity.mostrarError(respuesta.Mensaje);
        }
        else
        {
            FiestaClickListener listener = new FiestaClickListener();
            activity.mostrarFiestas(new MisFiestasAdapter(respuesta.Modelo, contexto, listener));
        }
    }
}
