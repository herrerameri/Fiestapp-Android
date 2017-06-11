package com.mint.fiestapp.presenters.fotos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fotos.ISubirFotosActivity;
import com.mint.fiestapp.views.fotos.SubirFotosActivity;

import java.util.List;

public class SubirFotosPresenter implements ISubirFotosPresenter {

    //IFotosModel fotosModel = new FotosModel(this);
    ISubirFotosActivity activity;
    Context contexto;
    String keyFiesta;
    List<FotoModel> fotosASubir;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (ISubirFotosActivity)activity;
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
        Intent intent = new Intent(context, SubirFotosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void agregarFoto(byte[] bytesFoto){
        FotoModel nuevaFoto = new FotoModel();
        nuevaFoto.Bytes = bytesFoto;
        fotosASubir.add(nuevaFoto);
    }
}
