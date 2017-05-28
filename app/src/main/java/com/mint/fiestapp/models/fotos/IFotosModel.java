package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.IModel;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.presenters.IPresenter;

public interface IFotosModel extends IModel {
    void obtenerFotos(int cantidad, String keyFiesta);
}
