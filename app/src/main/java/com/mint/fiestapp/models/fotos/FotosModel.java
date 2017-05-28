package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.services.fotos.FotosService;
import com.mint.fiestapp.services.fotos.IFotosService;

import java.util.List;

public class FotosModel implements IFotosModel, FotosService.IFotosServiceCallback {
    IFotosService services = new FotosService(this);

    public interface IFotosModelCallback{
        void mostrarFotos(List<FotoModel> modelo);
        void mostrarError(String mensaje);
    }

    IFotosModelCallback listener;

    public FotosModel(IFotosModelCallback callback){
        listener = callback;
    }

    @Override
    public void obtenerFotos(int cantidad, String keyFiesta) {
        services.obtenerUltimasFotos(cantidad, keyFiesta);
    }

    @Override
    public void callbackObtenerFotos(RespuestaLista<FotoModel> result) {
        if(result.Exito){
            listener.mostrarFotos(result.Modelo);
        }
        else{
            listener.mostrarError(result.Mensaje);
        }
    }

}
