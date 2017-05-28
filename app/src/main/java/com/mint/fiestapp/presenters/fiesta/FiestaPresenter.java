package com.mint.fiestapp.presenters.fiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.fotos.FotosModel;
import com.mint.fiestapp.models.fotos.IFotosModel;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fiesta.FiestaActivity;
import com.mint.fiestapp.views.fiesta.FotosView;
import com.mint.fiestapp.views.fiesta.IFiestaActivity;

import java.util.List;

public class FiestaPresenter implements IFiestaPresenter, FotosModel.IFotosModelCallback {

    IFotosModel fotosModel = new FotosModel(this);
    IFiestaActivity activity;
    Context contexto;
    FiestaModel model;
    FotosView viewFotos;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (IFiestaActivity)activity;
    }

    @Override
    public void setContext(Context context) {
        contexto = context;
    }

    @Override
    public void setFiesta(FiestaModel model){
        this.model = model;
    }

    @Override
    public int getTotalFuncionalidades() {
        return model.Funcionalidades.size();
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

    @Override
    public View getLayoutFuncionalidad(int indice) {
        switch (model.Funcionalidades.get(indice))
        {
            case ASISTENCIA:
            case GALERIA:
                viewFotos = new FotosView();
                fotosModel.obtenerFotos(8, model.key);
                return viewFotos.getLayout(contexto);
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
    public void mostrarFotos(List<FotoModel> modelo) {
        viewFotos.setFotos(modelo);
    }

    @Override
    public void mostrarError(String mensaje) {

    }
}
