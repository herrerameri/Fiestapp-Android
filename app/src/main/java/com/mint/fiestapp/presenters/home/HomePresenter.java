package com.mint.fiestapp.presenters.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.misfiestas.IMisFiestasModel;
import com.mint.fiestapp.models.misfiestas.MisFiestasModel;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.MainActivity;
import com.mint.fiestapp.views.home.HomeActivity;
import com.mint.fiestapp.views.home.IHomeActivity;

import java.util.List;

public class HomePresenter implements IHomePresenter, MisFiestasModel.IMisFiestasModelCallback {

    IMisFiestasModel model = new MisFiestasModel(this);
    List<FiestaModel> fiestas;
    IHomeActivity activity;
    Context contexto;

    @Override
    public void iniciarActivity(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
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
        activity = (IHomeActivity)vista;
    }

    @Override
    public void obtenerFiestas(){
        activity.mostrarProgreso();
        model.obtenerFiestas();
    }

    @Override
    public void volverALogin(){
        Intent intent = new Intent(contexto, MainActivity.class);
        contexto.startActivity(intent);
    }

    @Override
    public void agregarFiesta(String codigo){
        if(!existeFiesta(codigo)){
            activity.mostrarProgreso();
            model.obtenerFiestasPorCodigo(codigo);
        }
        else {
            mostrarMensaje(contexto.getResources().getString(R.string.ya_existe_fiesta));
        }
    }

    private boolean existeFiesta(String codigo){
        if(fiestas == null)
            return false;
        for (FiestaModel fiesta: fiestas) {
            if(fiesta.Codigo.equals(codigo))
                return true;
        }
        return false;
    }

    //region MisFiestasModel.IMisFiestasModelCallback
    @Override
    public void mostrarFiestas(List<FiestaModel> modelo){
        activity.ocultarProgreso();
        FiestaClickListener listener = new FiestaClickListener();
        fiestas = modelo;
        activity.mostrarFiestas(new MisFiestasAdapter(modelo, contexto, listener));
    }

    @Override
    public void mostrarMensaje(String mensaje){
        activity.ocultarProgreso();
        activity.mostrarMensaje(mensaje);
    }
    //endregion
}
