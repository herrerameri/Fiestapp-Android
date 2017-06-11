package com.mint.fiestapp.presenters.fotos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.models.fotos.FotosModel;
import com.mint.fiestapp.models.fotos.IFotosModel;
import com.mint.fiestapp.presenters.misfiestas.FiestaClickListener;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fotos.FotosActivity;
import com.mint.fiestapp.views.fotos.IFotosActivity;

import java.util.List;

public class FotosPresenter implements IFotosPresenter, FotosModel.IFotosModelCallback, FotosAdapter.OnFotoClickListener {

    IFotosModel fotosModel = new FotosModel(this);
    FotosAdapter adapterFotos;
    IFotosActivity activity;
    Context contexto;
    String keyFiesta;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (IFotosActivity)activity;
    }

    @Override
    public void setContext(Context context) {
        contexto = context;
    }

    @Override
    public void setKeyFiesta(String key){
        this.keyFiesta = key;
    }

    @Override
    public void iniciarActivity(Context context) {
        Intent intent = new Intent(context, FotosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void obtenerFotos(){
        activity.mostrarProgreso();
        fotosModel.obtenerFotos(activity.PAGE_SIZE, activity.getKeyUltimaFoto(), keyFiesta);
    }

    @Override
    public void obtenerMasFotos(){
        activity.mostrarProgreso();
        fotosModel.obtenerFotos(activity.PAGE_SIZE, activity.getKeyUltimaFoto(), keyFiesta);
    }

    //region FotosModel.IFotosModelCallback
    @Override
    public void mostrarFotos(List<FotoModel> modelo) {
        activity.ocultarProgreso();
        if(modelo.size() > 0){
            activity.setKeyUltimaFoto(modelo.get(0).key);
        }
        if(adapterFotos == null){
            adapterFotos = new FotosAdapter(modelo, contexto, this);
            activity.mostrarFotos(adapterFotos);
        }
        else{
            if(modelo.size() < activity.PAGE_SIZE){
                activity.setUltimaPagina(true);
            }
            adapterFotos.addDatos(modelo);
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        activity.ocultarProgreso();
        // TODO
    }

    @Override
    public void setUsuariosFoto(UsuarioModel usuario) {
        adapterFotos.setUsuario(usuario.Id, usuario.Nombre);
    }
    //endregion


    @Override
    public void onFotoClick(Context context, FotoModel item) {

    }
}
