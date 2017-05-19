package com.mint.fiestapp.presenters.fiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fiesta.FiestaActivity;
import com.mint.fiestapp.views.fiesta.IFiestaActivity;

public class FiestaPresenter implements IFiestaPresenter {

    IFiestaActivity activity;
    Context contexto;
    FiestaModel model;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (IFiestaActivity)activity;
    }

    @Override
    public void setContext(Context context) {
        contexto = context;
    }

    public void setFiesta(FiestaModel model){
        this.model = model;
    }

    @Override
    public int totalFuncionalidades() {
        return model.Funcionalidades.size();
    }

    @Override
    public int idIconoFuncionalidad(int indice) {
        return R.mipmap.ic_insert_photo;//model.Funcionalidades.get(indice).;
    }

    @Override
    public String getTitulo() {
        return model.Nombre;
    }

    @Override
    public String getDescripcion() {
        return model.Detalle;
    }

    @Override
    public String getFoto() {
        return model.Imagen;
    }

    @Override
    public String getCantidadFotos() {
        return String.format("%d", model.CantidadFotos);
    }

    @Override
    public String getCantidadInvitados() {
        return String.format("%d", model.CantidadInvitados);
    }

    @Override
    public String getCantidadDias() {
        return String.format("%d", model.CantidadDias);
    }

    @Override
    public void iniciarActivity(Context context) {
        Intent intent = new Intent(context, FiestaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
