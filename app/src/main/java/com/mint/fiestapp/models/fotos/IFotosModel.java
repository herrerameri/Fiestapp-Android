package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.IModel;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.presenters.IPresenter;

public interface IFotosModel extends IModel {
    void obtenerFotos(int cantidad, String keyUltimaFoto, String keyFiesta);
    void obtenerFotosConectado(int cantidad, String keyFiesta);
    void agregarReaccion(String keyFiesta, String keyFoto, String idUsuario);
    void quitarReaccion(String keyFiesta, String keyFoto, String keyReaccion);
}
