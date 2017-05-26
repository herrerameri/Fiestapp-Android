package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.IModel;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.presenters.IPresenter;

import java.io.Serializable;

public interface IMisFiestasModel extends IModel {
    void obtenerFiestas(IPresenter presenter);
    void callbackObtenerFiestas(RespuestaLista<FiestaModel> modelo);
}
