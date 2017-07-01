package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.services.misfiestas.IMisFiestasService;
import com.mint.fiestapp.services.misfiestas.MisFiestasService;

import java.util.List;


public class MisFiestasModel implements IMisFiestasModel, MisFiestasService.IMisFiestasServiceCallback {
    IMisFiestasService services = new MisFiestasService(this);

    public interface IMisFiestasModelCallback{
        void mostrarFiestas(List<FiestaModel> modelo);
        void mostrarMensaje(String mensaje);
    }

    IMisFiestasModelCallback listener;

    public MisFiestasModel(IMisFiestasModelCallback listener){
        this.listener = listener;
    }

    @Override
    public void obtenerFiestas(){
        services.obtenerFiestas();
    }

    @Override
    public void obtenerFiestasPorCodigo(String codigo){
        services.obtenerFiestasPorCodigo(codigo);
    }

    @Override
    public void callbackObtenerFiestas(RespuestaLista<FiestaModel> result) {
        if(result.Exito){
            listener.mostrarFiestas(result.Modelo);
        }
        else{
            listener.mostrarMensaje(result.Mensaje);
        }
    }

    @Override
    public void callbackAgregarFiesta(RespuestaLista<Object> respuesta) {
        listener.mostrarMensaje(respuesta.Mensaje);
    }
}
