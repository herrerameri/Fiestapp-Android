package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.Respuesta;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.services.facebook.FacebookService;
import com.mint.fiestapp.services.facebook.IFacebookService;
import com.mint.fiestapp.services.fotos.FotosService;
import com.mint.fiestapp.services.fotos.IFotosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FotosModel implements IFotosModel, FotosService.IFotosServiceCallback,FacebookService.IFacebookServiceCallback {
    IFotosService services = new FotosService(this);
    IFacebookService facebookServices = new FacebookService(this);
    public interface IFotosModelCallback{
        void mostrarFotos(List<FotoModel> modelo);
        void mostrarError(String mensaje);
        void setUsuariosFoto(UsuarioModel usuario);
        void agregarReaccion(String keyFoto, HashMap<String,UsuarioModel> reaccion);
        void quitarReaccion(String keyFoto, String keyReaccion);
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
    public void obtenerFotosConectado(int cantidad, String keyFiesta) {
        services.obtenerFotosConectado(cantidad, keyFiesta);
    }

    @Override
    public void agregarReaccion(String keyFiesta, String keyFoto, String idUsuario){
        services.agregarReaccion(keyFiesta, keyFoto, idUsuario);
    }

    @Override
    public void quitarReaccion(String keyFiesta, String keyFoto, String keyReaccion){
        services.quitarReaccion(keyFiesta, keyFoto, keyReaccion);
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
    public void callbackObtenerFotosConectado(RespuestaLista<FotoModel> result) {
        if(result.Exito){
            listener.mostrarFotos(result.Modelo);
        }
    }

    @Override
    public void callbackAgregarReaccion(String keyFoto, Respuesta<HashMap<String, UsuarioModel>> result) {
        if(result.Exito){
            listener.agregarReaccion(keyFoto, result.Modelo);
        }
        else{
            listener.mostrarError(result.Mensaje);
        }
    }

    @Override
    public void callbackQuitarReaccion(String keyFoto, Respuesta<String> result) {
        if(result.Exito){
            listener.quitarReaccion(keyFoto, result.Modelo);
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
