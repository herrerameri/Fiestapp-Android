package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;
import com.mint.fiestapp.services.misfiestas.IMisFiestasService;
import com.mint.fiestapp.services.misfiestas.MisFiestasService;


public class MisFiestasModel implements IMisFiestasModel {
    IMisFiestasService services = new MisFiestasService(this);
    IPresenter presenter;

    public void obtenerFiestas(IPresenter presenter){
        this.presenter = presenter;
        services.obtenerFiestas();
    }

    public void callbackObtenerFiestas(RespuestaLista<FiestaModel> result) {
        if(result.Exito){
            ((MisFiestasPresenter)presenter).mostrarFiestas(result.Modelo);
        }
        else{
            ((MisFiestasPresenter)presenter).mostrarError(result.Mensaje);
        }
    }
}
