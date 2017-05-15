package com.mint.fiestapp.presenters.misfiestas;

import android.content.Context;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.misfiestas.IMisFiestasModel;
import com.mint.fiestapp.models.misfiestas.MisFiestasModel;
import com.mint.fiestapp.views.misfiestas.IMisFiestasActivity;

import java.util.ArrayList;
import java.util.List;

public class MisFiestasPresenter implements IMisFiestasPresenter {

    IMisFiestasModel model = new MisFiestasModel();
    IMisFiestasActivity vista;
    Context contexto;

    public MisFiestasPresenter(IMisFiestasActivity view, Context context){
        vista = view;
        contexto = context;
    }

    public void ObtenerFiestas(){
        RespuestaLista<FiestaModel> respuesta = model.ObtenerFiestas();

        if(!respuesta.Exito)
        {
            vista.mostrarError(respuesta.Mensaje);
        }
        else
        {
            FiestaClickListener listener = new FiestaClickListener();
            vista.mostrarFiestas(new MisFiestasAdapter(respuesta.Modelo, listener));
        }
    }
}
