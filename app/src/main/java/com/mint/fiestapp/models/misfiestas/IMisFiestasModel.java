package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;

import java.io.Serializable;

public interface IMisFiestasModel extends Serializable {
    RespuestaLista<FiestaModel> obtenerFiestas();
}
