package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;

public interface IMisFiestasModel {
    RespuestaLista<FiestaModel> ObtenerFiestas();
}
