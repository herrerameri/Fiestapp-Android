package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.services.facebook.FacebookService;
import com.mint.fiestapp.services.facebook.IFacebookService;
import com.mint.fiestapp.services.fotos.FotosService;
import com.mint.fiestapp.services.fotos.IFotosService;
import com.mint.fiestapp.services.fotos.ISubirFotosService;
import com.mint.fiestapp.services.fotos.SubirFotosService;

import java.util.ArrayList;
import java.util.List;

public class SubirFotosModel implements ISubirFotosModel, SubirFotosService.ISubirFotosServiceCallback {
    ISubirFotosService services = new SubirFotosService(this);

    @Override
    public void callbackSubirFotos(RespuestaLista<Object> respuesta) {

    }

    public interface ISubirFotosModelCallback{
    }

    ISubirFotosModelCallback listener;

    public SubirFotosModel(ISubirFotosModelCallback callback){
        listener = callback;
    }

    @Override
    public void subirFotos(List<FotoModel> modelo) {
        services.subirFotos(modelo);
    }
}
