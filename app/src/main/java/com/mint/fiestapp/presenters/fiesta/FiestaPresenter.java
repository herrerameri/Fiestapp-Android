package com.mint.fiestapp.presenters.fiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    public View getLayoutFuncionalidad(int indice) {
        switch (model.Funcionalidades.get(indice))
        {
            case ASISTENCIA:
            case GALERIA:
            case MENU:
            case MEGUSTAS:
            case VESTIMENTA:
            case REGALOS:
            case MUSICA:
            default:
        }
        return null;
    }

    @Override
    public double getLatitud() { return Double.parseDouble(model.Ubicacion.Latitud); }

    @Override
    public double getLongitud() { return Double.parseDouble(model.Ubicacion.Longitud); }

    @Override
    public String getFecha() { return model.DiaMes(); }

    @Override
    public String getHora() { return model.Hora(); }

    @Override
    public String getNombreUbicacion() { return model.Ubicacion.Nombre; }

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
