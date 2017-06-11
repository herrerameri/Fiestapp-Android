package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.services.facebook.FacebookService;
import com.mint.fiestapp.services.facebook.IFacebookService;
import com.mint.fiestapp.services.fotos.FotosService;
import com.mint.fiestapp.services.fotos.IFotosService;

import java.util.ArrayList;
import java.util.List;

public class FotosModel implements IFotosModel, FotosService.IFotosServiceCallback,FacebookService.IFacebookServiceCallback {
    IFotosService services = new FotosService(this);
    IFacebookService facebookServices = new FacebookService(this);
    public interface IFotosModelCallback{
        void mostrarFotos(List<FotoModel> modelo);
        void mostrarError(String mensaje);
        void setUsuariosFoto(UsuarioModel usuario);
    }

    IFotosModelCallback listener;

    public FotosModel(IFotosModelCallback callback){
        listener = callback;
    }

    @Override
    public void obtenerFotos(int cantidad, String keyUltimaFoto, String keyFiesta) {
        if(keyUltimaFoto.equals("")){
            services.obtenerUltimasFotos(cantidad, keyFiesta);
        }
        else{
            services.obtenerPaginaFotos(cantidad, keyUltimaFoto, keyFiesta);
        }
    }

    @Override
    public void callbackObtenerFotos(RespuestaLista<FotoModel> result) {
        if(result.Exito){
            List<String> Ids = new ArrayList<>();
            for (FotoModel foto : result.Modelo) {
                if(!Ids.contains(foto.Usuario.Id)){
                    facebookServices.getDisplayName(foto.Usuario.Id);
                    Ids.add(foto.Usuario.Id);
                }
            }
            listener.mostrarFotos(result.Modelo);
        }
        else{
            listener.mostrarError(result.Mensaje);
        }
    }

    @Override
    public void callbackObtenerNombre(UsuarioModel respuesta) {
        listener.setUsuariosFoto(respuesta);
    }
}
