package com.mint.fiestapp.presenters.fiesta;

import android.view.View;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.presenters.IPresenter;

public interface IFiestaPresenter extends IPresenter {
    void setFiesta(FiestaModel model);
    int getTotalFuncionalidades();
    View getLayoutFuncionalidad(int indice);

    double getLatitud();
    double getLongitud();
    String getFecha();
    String getHora();
    String getNombreUbicacion();

    String getTitulo();
    String getDescripcion();
    String getCantidadFotos();
    String getCantidadInvitados();
    String getCantidadDias();
    String getFoto();
}
