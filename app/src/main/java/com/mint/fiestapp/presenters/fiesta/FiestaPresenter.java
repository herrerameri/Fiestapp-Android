package com.mint.fiestapp.presenters.fiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.EnumFuncionalidades;
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
    public int getTotalFuncionalidades() {
        return model.Funcionalidades.size();
    }

    @Override
    public int getIdIconoFuncionalidad(int indice) {
        switch (model.Funcionalidades.get(indice))
        {
            case ASISTENCIA:
                return R.mipmap.ic_pan_tool;
            case GALERIA:
                return R.mipmap.ic_insert_photo;
            case INFORMACION:
                return R.mipmap.ic_place;
            case MENU:
                return R.mipmap.ic_restaurant;
            case MEGUSTAS:
                return R.mipmap.ic_favorite;
            case VESTIMENTA:
                return R.mipmap.ic_action_notifications;
            case REGALOS:
                return R.mipmap.ic_action_notifications;
            case MUSICA:
                return R.mipmap.ic_action_notifications;
            default:
                return R.mipmap.ic_insert_photo;
        }
    }

    @Override
    public void clickOnFuncionalidad(int indice){

    }

    @Override
    public String getTituloFuncionalidad(int indice) {
        return model.Funcionalidades.get(indice).getNombre();
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
