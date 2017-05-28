package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;
import com.mint.fiestapp.services.misfiestas.IMisFiestasService;
import com.mint.fiestapp.services.misfiestas.MisFiestasService;

import java.util.List;


public class MisFiestasModel implements IMisFiestasModel, MisFiestasService.IMisFiestasServiceCallback {
    IMisFiestasService services = new MisFiestasService(this);

    public interface IMisFiestasModelCallback{
        void mostrarFiestas(List<FiestaModel> modelo);
        void mostrarError(String mensaje);
    }

    IMisFiestasModelCallback listener;

    public MisFiestasModel(IMisFiestasModelCallback listener){
        this.listener = listener;
    }

    public void obtenerFiestas(){
        services.obtenerFiestas();
    }

    @Override
    public void callbackObtenerFiestas(RespuestaLista<FiestaModel> result) {
        if(result.Exito){
            listener.mostrarFiestas(result.Modelo);
        }
        else{
            listener.mostrarError(result.Mensaje);
        }
    }
}
