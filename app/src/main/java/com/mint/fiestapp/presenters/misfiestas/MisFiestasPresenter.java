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
import java.util.List;

public class MisFiestasPresenter implements IMisFiestasPresenter, MisFiestasModel.IMisFiestasModelCallback {

    IMisFiestasModel model = new MisFiestasModel(this);
    IMisFiestasActivity activity;
    Context contexto;

    @Override
    public void iniciarActivity(Context context){
        Intent intent = new Intent(context, MisFiestasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setContext(Context context){
        contexto = context;
    }

    @Override
    public void setActivity(IActivity vista){
        activity = (IMisFiestasActivity)vista;
    }

    @Override
    public void obtenerFiestas(){
        activity.mostrarProgreso();
        model.obtenerFiestas();
    }

    //region MisFiestasModel.IMisFiestasModelCallback
    @Override
    public void mostrarFiestas(List<FiestaModel> modelo){
        FiestaClickListener listener = new FiestaClickListener();
        activity.mostrarFiestas(new MisFiestasAdapter(modelo, contexto, listener));
    }

    @Override
    public void mostrarError(String mensaje){
        activity.mostrarError(mensaje);
    }
    //endregion
}
