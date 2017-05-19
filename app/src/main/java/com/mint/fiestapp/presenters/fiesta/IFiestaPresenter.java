package com.mint.fiestapp.presenters.fiesta;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.presenters.IPresenter;

public interface IFiestaPresenter extends IPresenter {
    void setFiesta(FiestaModel model);
    int totalFuncionalidades();
    int idIconoFuncionalidad(int indice);
    String getTitulo();
    String getDescripcion();
    String getCantidadFotos();
    String getCantidadInvitados();
    String getCantidadDias();
    String getFoto();
}
